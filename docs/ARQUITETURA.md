# Arquitetura do Atenda360

## Visão geral

O Atenda360 usa um monólito modular no backend e uma aplicação Angular organizada por funcionalidades. Isso reduz a complexidade operacional do MVP sem impedir crescimento posterior.

```text
Angular 20 → HTTP/JSON + Bearer JWT → Spring Boot 3.5 → JPA/Hibernate → PostgreSQL
```

## Backend

Os domínios `auth`, `empresa`, `usuario`, `cliente`, `agendamento`, `atendimento` e `dashboard` são separados por pacote. Segurança, configuração e tratamento de erros ficam em módulos transversais.

O MVP separa:

- `Agendamento`: cliente, responsável e horário planejado;
- `Atendimento`: início/fim real, observações e resultado.

Essa divisão permite medir atrasos, duração real, cancelamentos e ausências sem perder o plano original.

## Multiempresa

As entidades operacionais pertencem obrigatoriamente a uma `Empresa`. O JWT recebe a claim `empresaId`; o filtro de segurança a disponibiliza ao `TenantService`. Repositórios e controllers consultam simultaneamente `id` e `empresaId`, impedindo acesso cruzado por identificador.

Antes da produção, devem ser adicionados testes explícitos de isolamento, auditoria e uma estratégia de renovação/revogação de tokens.

## Persistência

No desenvolvimento, `spring.jpa.hibernate.ddl-auto=update` acelera o schema. Antes do primeiro deploy produtivo:

1. registrar o schema atual como migration inicial;
2. introduzir Flyway;
3. alterar `ddl-auto` para `validate`;
4. revisar índices com dados reais.

## Frontend

Os componentes Angular são standalone e organizados em `core` e `features`. O interceptor injeta o JWT nas chamadas HTTP. As telas usam dados demonstrativos locais nesta fatia, enquanto a API fornece os contratos reais para integração.

Decisões deliberadas: sem microserviços, Docker, migrations, NgRx ou bibliotecas adicionais de formulário nesta versão.
