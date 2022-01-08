# tqi_evolution_backend_2021



## Sobre o Projeto

Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:
i. Cadastro de clientes
    O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.
ii. Login
    A autenticação será realizada por e-mail e senha.
iii. Solicitação de empréstimo
    Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.
    O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.
iv. Acompanhamento das solicitações de empréstimo
    O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
    Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
    No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

## Tecnologias Utilizadas

* Java 11
  * Spring Data
  * Spring Security
* PostgreSQL
* Git
* Docker

## Documentação

Documentação da API está disponível [aqui](https://github.com/afonsoneto121/tqi_evolution_backend_2021/blob/main/DOCUMENTATION.md)

## Como executar o projeto

 Para executar o projeto é necessário ter instalado localmente o docker, docker-compose e Java(opcional) na versão 11. 

* O primeiro passo é baixar o repositório 

```sh
git clone https://github.com/afonsoneto121/-tqi_evolution_backend_2021.git
```

Há duas formas de iniciar o projeto (i) via gradle e (iI) via docker

### Via Gradle (Linux)

Na raiz do projeto iniciar banco de dados dia docker

```sh
cd docker && \
docker-compose up --build -d && \
cd ..
```

Compilar a aplicação

```sh
./gradlew build
```

ou 

```sh
./gradlew build -x test
```

Para pular a execução dos testes(mais rápido).

Em seguida executar

```sh
cd build/libs/ && \
java -jar tqi_evolution_backend_2021-0.0.1-SNAPSHOT.jar
```

A aplicação deverá iniciar no terminal 

### Via Docker 

...
