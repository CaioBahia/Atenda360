[CmdletBinding()]
param(
    [switch]$NoBrowser
)

$ErrorActionPreference = 'Stop'
$workspace = Split-Path -Parent $PSScriptRoot
$apiDirectory = Join-Path $workspace 'atenda360-api'
$webDirectory = Join-Path $workspace 'atenda360-web'
$runtimeDirectory = Join-Path $workspace '.run'
$logDirectory = Join-Path $runtimeDirectory 'logs'

function Test-ProcessRunning([string]$PidFile) {
    if (-not (Test-Path -LiteralPath $PidFile)) { return $false }
    $savedPid = (Get-Content -LiteralPath $PidFile -Raw).Trim()
    if ($savedPid -notmatch '^\d+$') { return $false }
    return $null -ne (Get-Process -Id ([int]$savedPid) -ErrorAction SilentlyContinue)
}

function Wait-Http([string]$Url, [string]$Name, [int]$Seconds = 60) {
    $deadline = (Get-Date).AddSeconds($Seconds)
    while ((Get-Date) -lt $deadline) {
        try {
            Invoke-WebRequest -Uri $Url -UseBasicParsing -TimeoutSec 3 | Out-Null
            Write-Host "$Name pronto." -ForegroundColor Green
            return
        } catch {
            Start-Sleep -Seconds 2
        }
    }
    throw "$Name não respondeu em $Url. Consulte os logs em $logDirectory."
}

foreach ($requiredCommand in @('java', 'node', 'npm')) {
    if (-not (Get-Command $requiredCommand -ErrorAction SilentlyContinue)) {
        throw "Comando obrigatório não encontrado: $requiredCommand"
    }
}

New-Item -ItemType Directory -Path $logDirectory -Force | Out-Null
$apiPidFile = Join-Path $runtimeDirectory 'api.pid'
$webPidFile = Join-Path $runtimeDirectory 'web.pid'

if (-not (Test-Path -LiteralPath (Join-Path $webDirectory 'node_modules'))) {
    Write-Host 'Instalando dependências do frontend pela primeira vez...'
    & npm.cmd install --prefix $webDirectory
    if ($LASTEXITCODE -ne 0) { throw 'Falha ao instalar as dependências do frontend.' }
}

if (-not (Test-ProcessRunning $apiPidFile)) {
    Write-Host 'Iniciando API com banco H2 de demonstração...'
    $apiProcess = Start-Process -FilePath 'cmd.exe' `
        -ArgumentList @('/d', '/c', 'mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=demo') `
        -WorkingDirectory $apiDirectory -WindowStyle Hidden -PassThru `
        -RedirectStandardOutput (Join-Path $logDirectory 'api.out.log') `
        -RedirectStandardError (Join-Path $logDirectory 'api.err.log')
    Set-Content -LiteralPath $apiPidFile -Value $apiProcess.Id
} else {
    Write-Host 'API já está em execução.' -ForegroundColor Yellow
}

Wait-Http 'http://localhost:8080/v3/api-docs' 'API'

if (-not (Test-ProcessRunning $webPidFile)) {
    Write-Host 'Iniciando aplicação web...'
    $webProcess = Start-Process -FilePath 'cmd.exe' `
        -ArgumentList @('/d', '/c', 'npm.cmd start -- --host 127.0.0.1') `
        -WorkingDirectory $webDirectory -WindowStyle Hidden -PassThru `
        -RedirectStandardOutput (Join-Path $logDirectory 'web.out.log') `
        -RedirectStandardError (Join-Path $logDirectory 'web.err.log')
    Set-Content -LiteralPath $webPidFile -Value $webProcess.Id
} else {
    Write-Host 'Frontend já está em execução.' -ForegroundColor Yellow
}

Wait-Http 'http://localhost:4200' 'Frontend'

Write-Host ''
Write-Host 'Atenda360 iniciado com sucesso.' -ForegroundColor Cyan
Write-Host 'Aplicação: http://localhost:4200'
Write-Host 'Swagger:   http://localhost:8080/swagger-ui.html'
Write-Host 'Admin:     admin@clinicaplena.com.br / 123456'
Write-Host 'Atendente: atendente@clinicaplena.com.br / 123456'
Write-Host 'Encerrar:  .\scripts\stop-demo.ps1'

if (-not $NoBrowser) {
    Start-Process 'http://localhost:4200'
}
