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
  * Caso o id passado na URL seja diferente do usuário válido para o token atual

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
  * Caso o id passado na URL seja diferente do usuário válido para o token atual

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
  * Caso o id passado na URL seja diferente do usuário válido para o token atual
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
  * Caso a data seja inválida ou o número de parcelas seja mair que 60
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válido para o token atual

## Todos os empréstimos de um usuário

```:arrow_right:
GET /api/v1/loans/{idUser}/loans?page=0&size=5&sort=value,asc
```

**Parâmetros**

* page :arrow_right: pagina 
* size :arrow_right: tamanho da página
* sort :arrow_right: ordenação. Ex: nome_do_atributo,desc ou nome_do_atributo,asc

**Header**

```
AUTHORIZATION Bearer token
```

**Response**

* 200 OK
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válido para o token atual

## Detalhes de um empréstimo 

```
GET /api/v1/loans/details/{IdEmprestimo}
```

**Header**

```
AUTHORIZATION Bearer token
```

**Response**

* 200 OK
* 401 Unauthorized 
  * Caso o usuário dono do empréstimo não seja o usuário válido para o token atual
* 404 NOT FOUND
  * Caso o empréstimo não seja encontrado
