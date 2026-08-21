# Atenda360

Plataforma de gestão de atendimentos para empresas prestadoras de serviço. O MVP organiza clientes, agenda, execução do atendimento e indicadores em uma experiência única, responsiva e multiempresa.

## O que já está no MVP

- login com autenticação JWT;
- dashboard com indicadores, evolução semanal, status e desempenho da equipe;
- agenda mensal/semanal com FullCalendar e criação rápida de agendamentos;
- cadastro e busca de clientes;
- quadro operacional de atendimentos com mudança de status;
- configurações básicas da empresa e da agenda;
- API REST documentada com OpenAPI/Swagger;
- isolamento dos dados pelo `empresa_id` presente no token;
- carga inicial de demonstração no perfil `dev`.

## Stack

Frontend: Angular 20, TypeScript, Angular Material, SCSS, FullCalendar e Chart.js/ng2-charts.

Backend: Java 21, Spring Boot 3.5, Spring Security/JWT, Spring Data JPA/Hibernate, PostgreSQL, Bean Validation, OpenAPI, JUnit 5 e Maven.

O projeto não usa Docker, Flyway ou Liquibase nesta etapa. Em desenvolvimento, o Hibernate gerencia o schema com `ddl-auto=update`.

## Estrutura

```text
Atenda360/
├── atenda360-web/   # aplicação Angular
├── atenda360-api/   # API Spring Boot
└── docs/            # arquitetura e contratos
```

## Pré-requisitos

- Node.js 20 ou superior;
- Java 21 ou superior;
- PostgreSQL 15 ou superior.

Não é necessário instalar Maven globalmente: o repositório inclui Maven Wrapper.

## Executando localmente

### Demonstração com um comando (recomendado)

O modo de demonstração não exige PostgreSQL. Ele cria um banco H2 em memória, carrega usuários e clientes de exemplo, inicia a API e o frontend e abre o navegador:

```powershell
.\scripts\start-demo.ps1
```

Para iniciar sem abrir o navegador:

```powershell
.\scripts\start-demo.ps1 -NoBrowser
```

Para encerrar completamente API e frontend:

```powershell
.\scripts\stop-demo.ps1
```

Os processos ficam ocultos e seus logs são gravados em `.run/logs`. O banco H2 é recriado a cada inicialização, portanto alterações feitas durante uma demonstração são descartadas ao encerrar.

### Desenvolvimento com PostgreSQL

Crie o banco:

```sql
CREATE DATABASE atenda360;
```

Os padrões são usuário `postgres`, senha `postgres`, porta `5432`. Para personalizar:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/atenda360"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua-senha"
$env:JWT_SECRET="uma-chave-com-pelo-menos-32-caracteres"
```

Inicie a API:

```powershell
cd atenda360-api
.\mvnw.cmd spring-boot:run
```

A API fica em `http://localhost:8080` e o Swagger em `http://localhost:8080/swagger-ui.html`.

Em outro terminal, inicie o frontend:

```powershell
cd atenda360-web
npm install
npm start
```

Abra `http://localhost:4200`.

## Acesso de demonstração

Com o perfil `dev` (padrão local), a API cria automaticamente:

- administrador: `admin@clinicaplena.com.br` / `123456`
- atendente: `atendente@clinicaplena.com.br` / `123456`

Os dados visuais das telas ficam locais nesta primeira fatia; os contratos REST correspondentes já estão disponíveis para a integração progressiva.

## Qualidade

```powershell
cd atenda360-web
npm run build

cd ..\atenda360-api
.\mvnw.cmd test
```

Os testes da API usam H2 somente no ambiente de teste, em modo de compatibilidade PostgreSQL. A aplicação continua usando PostgreSQL.

## Documentação

- [Arquitetura e decisões](docs/ARQUITETURA.md)
- [API e regras de negócio](docs/API.md)
- [Experiência e interface](docs/UX.md)

## Próximas evoluções recomendadas

1. conectar todas as telas aos endpoints e adicionar estados de carregamento/erro;
2. ampliar testes unitários e de integração por módulo;
3. implementar recuperação de senha e convites de equipe;
4. adicionar filtros e exportação nos relatórios;
5. introduzir Flyway antes do primeiro ambiente produtivo;
6. criar auditoria, renovação de token e estratégia de revogação.
