[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$workspace = Split-Path -Parent $PSScriptRoot
$runtimeDirectory = Join-Path $workspace '.run'

function Stop-ProcessTree([string]$Name, [string]$PidFile) {
    if (-not (Test-Path -LiteralPath $PidFile)) {
        Write-Host "$Name já está parado." -ForegroundColor Yellow
        return
    }

    $savedPid = (Get-Content -LiteralPath $PidFile -Raw).Trim()
    if ($savedPid -match '^\d+$' -and (Get-Process -Id ([int]$savedPid) -ErrorAction SilentlyContinue)) {
        & taskkill.exe /PID $savedPid /T /F | Out-Null
        Write-Host "$Name encerrado." -ForegroundColor Green
    } else {
        Write-Host "$Name não estava mais em execução." -ForegroundColor Yellow
    }

    Remove-Item -LiteralPath $PidFile -Force
}

Stop-ProcessTree 'Frontend' (Join-Path $runtimeDirectory 'web.pid')
Stop-ProcessTree 'API' (Join-Path $runtimeDirectory 'api.pid')
Write-Host 'Atenda360 finalizado.' -ForegroundColor Cyan
