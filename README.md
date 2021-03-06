# tqi_evolution_backend_2021



## Sobre o Projeto

Por se tratar de uma aplicação bancária, o foco principal será na segurança dos dados. A autenticação é baseada em token JWT que por padrão se expira a cada 20 minutos e há apenas dois endpoints abertos para requisições: login e salvar novo usuário. O acesso aos dados é limita, isso significa que o usuária autenticado só consegue acessar suas próprias informações, qualquer requisição feita em um dado pertencente a outro usuário será respondida com status unauthorized. Para mais detalhes, consultar a documentação.

### Especificações 

Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:

1. Cadastro de clientes
   1. O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.
2. Login
   1. A autenticação será realizada por e-mail e senha.
3. Solicitação de empréstimo
   1. Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.
   2. O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.
4. Acompanhamento das solicitações de empréstimo
   1. O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
   2. Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
   3. No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.



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

Há duas formas de iniciar o projeto (i) via gradle e (ii) via docker

### Via Gradle (Linux)

Na raiz do projeto iniciar banco de dados dia docker

```sh
cd docker && \
docker-compose up --build -d && \
cd ..
```

Compilar a aplicação

```sh
./gradlew build --scan
```

ou 

```sh
./gradlew build --scan -x test
```

Para pular a execução dos testes(mais rápido).

Em seguida executar

```sh
cd build/libs/ && \
java -jar tqi_evolution_backend_2021-0.0.1-SNAPSHOT.jar
```

A aplicação deverá iniciar no terminal 

### Via Docker (Linux e Windows)

Neste caso não é necessário ter o Java instalado localmente.

Na raiz do projeto, inicie a aplicação

```sh
cd build-docker && \
docker-compose up --build -d
```

A aplicação irá iniciar em background, a menos que a flag -d seja omitida.
