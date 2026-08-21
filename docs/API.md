# API e regras de negócio

Base local: `http://localhost:8080/api`  
Swagger: `http://localhost:8080/swagger-ui.html`

## Autenticação

`POST /auth/login`

```json
{"email":"admin@clinicaplena.com.br","senha":"123456"}
```

Nas rotas protegidas, envie `Authorization: Bearer <token>`. O `empresaId` vem exclusivamente da claim do token.

## Clientes

- `GET /clientes?busca=` — lista/pesquisa;
- `GET /clientes/{id}` — detalhe;
- `POST /clientes` — cria;
- `PUT /clientes/{id}` — edita;
- `PATCH /clientes/{id}/status?ativo=false` — ativa/inativa.

## Agendamentos

- `GET /agendamentos?inicio=2026-08-01&fim=2026-08-31`;
- `POST /agendamentos`;
- `PUT /agendamentos/{id}`;
- `PATCH /agendamentos/{id}/status?status=CANCELADO`.

Status: `AGENDADO`, `CONFIRMADO`, `EM_ANDAMENTO`, `CONCLUIDO`, `CANCELADO`, `NAO_COMPARECEU`.

```json
{
  "clienteId": 1,
  "responsavelId": 1,
  "tipoAtendimento": "Consulta inicial",
  "inicio": "2026-08-20T14:00:00",
  "fim": "2026-08-20T14:45:00",
  "observacoes": "Primeiro contato"
}
```

O fim deve ser posterior ao início. Cliente e responsável precisam pertencer à empresa autenticada.

## Atendimentos e dashboard

- `POST /atendimentos/agendamento/{id}/iniciar`;
- `PATCH /atendimentos/{id}/concluir`;
- `GET /atendimentos/cliente/{clienteId}`;
- `GET /dashboard?mes=2026-08`.

Iniciar muda o agendamento para `EM_ANDAMENTO`; concluir salva os dados reais e muda para `CONCLUIDO`.

Erros usam `400` para validação, `401` para autenticação e `404` para registro inexistente ou de outra empresa.
