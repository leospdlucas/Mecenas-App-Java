
# Mecenas (Java / Spring Boot) — MVP

Inclui: obras + demos, campanha + doação (Stripe Checkout), ofertas, assinaturas (Checkout), presign S3 e leitor com marca d'água (Thymeleaf).

## Rodando local
Requisitos: Java 21, Maven 3.9+, PostgreSQL, Stripe CLI (webhooks opcional), AWS credenciais (S3).

```bash
createdb mecenas
mvn -pl server -am spring-boot:run
# Abra http://localhost:8080/  e depois /works/1
```

### Login (DEV)
Envie o header **X-Dev-Email** para assumir um usuário:
- `autor@mecenas.app` (AUTHOR)
- `parceiro1@cnpj.app` (PARTNER)

### Doação (campanha)
`POST /api/campaigns/{id}/pledges/checkout` com body `{"amountBrl": 1000}` → retorna `{ url }` (Stripe Checkout).  
Webhook: `POST /api/webhooks/stripe` marca o pledge como `PAID` (Stripe CLI: `stripe listen --forward-to localhost:8080/api/webhooks/stripe`).

### Assinaturas (Parceiros)
`POST /api/subscriptions/checkout` com body `{"plan":"monthly"}` → `{ url }` (usa `STRIPE_PRICE_*`).

### Ofertas
- `POST /api/offers` (PARTNER) → cria oferta (`SENT`).
- `POST /api/offers/{id}/accept|reject` (AUTHOR).

### Presign S3
`POST /api/demos/presign` com `{ "key": "uploads/demo.pdf", "contentType":"application/pdf" }`.

> Em produção: trocar o cabeçalho dev por OAuth/OIDC, tratar LGPD e logs/auditoria.
