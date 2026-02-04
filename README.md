# Basic CRUD (Spring Boot)

API REST simples para cadastro de itens em memória.

## Stack
- Java 17
- Spring Boot 3.3.2 (web + validation)
- Lombok

## Executando
```bash
mvn spring-boot:run
# ou
mvn clean package && java -jar target/crud-mem-0.0.1-SNAPSHOT.jar
```

> Se estiver em ambiente sem acesso à internet, a build pode falhar ao baixar dependências. Use um repositório Maven acessível ou execute onde haja rede.

## Modelo de dados
`Item`
- `id` (UUID)
- `name` (String)
- `description` (String)
- `price` (BigDecimal)
- `quantity` (Integer)
- `createdAt`, `updatedAt` (Instant)

## Rotas
Todas respondem e consomem `application/json`.

### Criar item
`POST /api/items`
```json
{ "name": "Mouse", "description": "Wireless", "price": 199.90, "quantity": 10 }
```
Resposta 201 com corpo do item e `Location` apontando para `/api/items/{id}`.

### Listar todos
`GET /api/items`
Retorna lista ordenada por `createdAt`.

### Buscar por id
`GET /api/items/{id}`
`id` deve ser UUID.

### Atualizar
`PUT /api/items/{id}`
```json
{ "name": "Mouse Pro", "description": "Wireless", "price": 249.90, "quantity": 5 }
```

### Excluir
`DELETE /api/items/{id}`

### Buscar com filtros (name/description/price)
`GET /api/items/search` ou `POST /api/items/search`

Body JSON (todos os campos opcionais):
```json
{ "name": "mouse", "description": "wire", "price": 199.90 }
```
- `name` e `description`: substring case-insensitive.
- `price`: igualdade exata.
Se nenhum campo for informado, retorna todos.

## Validação e erros
- Campos anotados com Bean Validation (ex.: `@NotBlank`, `@DecimalMin`).
- Erros retornam corpo:
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "BadRequest",
  "message": "ValidationError",
  "path": "/api/items",
  "requestId": "...",
  "violations": [
    { "field": "name", "message": "must not be blank" }
  ]
}
```
- `NotFoundException` retorna 404.
- Métodos não suportados retornam 405.

## Testes
```bash
mvn test
```
(há testes de controlador em `src/test/java` se adicionados; no momento não há testes ativos neste sandbox).

## Observações
- Repositório é in-memory (`ConcurrentHashMap`); dados somem ao reiniciar.
- Endpoints de busca aceitam GET ou POST com JSON no corpo; muitos clientes ignoram body em GET, prefira POST se houver proxy que descarte o corpo.
