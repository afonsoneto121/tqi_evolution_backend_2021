# Documentação

## Variáveis de Ambiente

* JWT_SECRET :arrow_right: Usado na geração do token JWT
* JWT_EXPIRATION :arrow_right: Tempo de expiração do token JWT em milisegundos
* DATABASE_URL :arrow_right: Url para acessar o banco de dados

## Rotas

Documentação gerada automaticamente pelo Postman https://documenter.getpostman.com/view/16544315/UVXerxdN

Todos os endpoints da API são protegidos, exceto o responsável por salvar o usuário e realizar o login.

## Salvar Usuário

```
POST /api/v1/users
```

**Body**

```json
{
    "name": "Foo",
    "email": "foo@bar.com",
    "password": "1234",
    "cpf": "123456789",
    "rg": "123456789",
    "address": "Rua teste",
    "income": 100
}
```

**Response**

* 201 CREATED
* 400 BAD REQUEST 
  * Se ouver erros na requisição ou o email já estiver sendo utilizado por outro usuário

## Login

```
POST /login
```

**Body**

```json
{
    "email": "foo@bar.com",
    "password": "1234"
}
```

**Response**

* Sucesso 200 - OK
  * Token JWT que será usado nas próximas requisições
* Falha 403 Forbidden

## Atualizar Usuário

```
PUT /api/v1/users/{idUser}
```

**Header**

```
AUTHORIZATION Bearer token
```

**Body**

```json
{
    "name": "Foo",
    "email": "foo@bar.com",
    "password": "1234",
    "cpf": "123456789",
    "rg": "1012131415",
    "address": "Rua teste",
    "income": 1000
}
```

**Response**

* 200 OK
* 400 Bad Request
  * Em caso de requisições com erros de escrita,
  * Caso novo email já esteja sendo usado por outro usuário
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válidado para o token atual

## Detetar usuário

```
DELETE /api/v1/users/{idUser}
```

**Header**

```
AUTHORIZATION Bearer token
```

**Response**

* 204 NO CONTENT
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válidado para o token atual

## Buscar usuário pelo Id

```
GET /api/v1/users/{idUser}
```

**Header**

```
AUTHORIZATION Bearer token
```

**Response**

* 200 OK
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válidado para o token atual
* 404 NOT FOUND
  * Caso usuário não for encontrado

## Salvar empréstimo

```
POST /api/v1/loans/{idUser}
```

 **Header**

```
AUTHORIZATION Bearer token
```

**Body**

```json
{
    "value": 5000,
    "dateFirstInstallment": "2022-04-02",
    "numberInstallment": 20
}
```

**Response**

* 201 CREATED
* 400 Bad Request
  * Em caso de requisições com erros de escrita,
  * Caso a data 
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válidado para o token atual
