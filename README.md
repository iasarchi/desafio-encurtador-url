# Encurtador de URL's

**Descrição:** 

Este projeto consiste em um encurtador de URL's simples, foi escolhido porque é simples e eficiente. Ele gera alias únicos e fáceis de lembrar.,com dois casos de uso:

- **Shorten URL:** Encurta uma URL e retorna o alias.
- **Retrieve URL:** Retorna a URL original a partir do alias.

# Tecnologias utilizadas
  "API"
- **Linguagem:** Java
- **Framework:** Spring Boot
- **ORM:** Hibernate
- **Banco de dados:** PostgreSQL
- **Bibliotecas de terceiros:**
    - **Junit:** Para testes unitários
    - **Mockito:** Para mocks
    - **Lombok:** Para getters, setters e constructors
    
"WEB"
- **Linguagem:**
    - **Typescript:**: como linguagem de programação
    - **HTML**: como linguagem de marcação
    - **CSS**: como linguagem de estilizacao
- **Framework:** angular/angular material

**Design da solução**

A solução foi dividida em três camadas:

- **Camada de apresentação:** Responsável pela interação com o usuário.
- **Camada de negócio:** Responsável pela lógica de negócio do encurtador de URL's.
- **Camada de dados:** Responsável pelo acesso ao banco de dados.

A camada de apresentação é implementada por uma API RESTful, desenvolvida com Spring Boot. A camada de negócio é implementada por classes Java, que representam as entidades do domínio (URL, Alias, etc.). A camada de dados é implementada pelo Hibernate, que mapeia as entidades do domínio para tabelas do banco de dados.

**Algoritmo para gerar alias**

Para gerar alias, foi utilizado o algoritmo abaixo:

1. Converta a URL para um hash MD5.
2. Remova os caracteres não alfanuméricos do hash.
3. Recorte o hash para 6 caracteres.

## Diagrama 1

**Descrição:** Este diagrama mostra o fluxo de execução do caso de uso "Shorten URL".

**Explicação:**

1. O usuário envia uma requisição HTTP POST para a API, com a URL original no corpo da requisição.
2. A API verifica se o alias fornecido pelo usuário já existe. Se existir, um erro é retornado.
3. Caso contrário, a API gera um novo alias para a URL original.
4. A API grava a URL original e o alias no banco de dados.
5. A API retorna a URL encurtada para o usuário.

![Diagrama 1](https://imgur.com/2D6ppFq.jpg)

**Imagem 1**

## Diagrama 2

**Descrição:** Este diagrama mostra o fluxo de execução do caso de uso "Fazer redirect com a URL encurtada".

**Explicação:**

1. O usuário envia uma requisição HTTP GET para a API, com o alias da URL encurtada no corpo da requisição.
2. A API verifica se o alias existe no banco de dados. Se não existir, um erro é retornado.
3. Caso contrário, a API recupera a URL original do banco de dados.
4. A API redireciona o usuário para a URL original.

![Diagrama 2](https://imgur.com/wrFVNOE.jpg)

**Imagem 2**

# Diagrama 3

**Descrição:** Este diagrama mostra o fluxo de execução do caso de uso "Consulta das top 10 URLs mais acessadas".

**Explicação:**

1. O usuário envia uma requisição HTTP GET para a API, com a URL da página que deseja consultar.
2. A API consulta o banco de dados para obter as 10 URLs mais acessadas.
3. A API retorna a lista das 10 URLs mais acessadas para o usuário.

![Diagrama 3](https://imgur.com/4mBvs8J.jpg)

**Imagem 3**

# Como rodar o projeto

Para rodar o projeto, siga as seguintes etapas:

1. Clone o repositório do GitHub.
2. Abra um terminal na pasta do projeto.
3. Execute os seguintes comandos para rodar a API:

`cd encurtador-url-api`

`mvn clean install`

`docker compose build`

`docker compose up`


1. Acesse a URL `http://localhost:8080` para testar a API.

Para rodar a aplicação web, siga as seguintes etapas:

1. Abra um terminal na pasta do projeto.
2. Execute os seguintes comandos para rodar a aplicação web:

`cd encurtador-url-web`

`npm install`

`ng serve`


Acesse a URL `http://localhost:4200` para testar a aplicação web.

**Exemplos de chamadas à API**

Para encurtar uma URL, execute a seguinte chamada HTTP:

`POST http://localhost:8080/shortener
{
    "customAlias": "aliastest",
    "url": "https://www.google.com/"
}`

A resposta será um objeto JSON com o seguinte formato:

`{
    "alias": "aliastest",
    "url": "http://localhost:8080/shortener/aliastest",
    "originalUrl": "https://www.google.com/",
    "timeTaken": "13ms"
}`

Para acessar uma URL encurtada, execute a seguinte chamada HTTP:

`GET http://localhost:8080/shortener/{alias}`

Onde `{alias}` é o alias da URL encurtada.

A resposta será um redirecionamento para a URL original.

Para consultar as 10 URLs mais acessadas, execute a seguinte chamada HTTP:

`GET http://localhost:8080/topten`

A resposta será um objeto JSON com o seguinte formato:

`[
    {
        "url": "https://www.google.com/",
        "clicks": 100
    },
    {
        "url": "https://www.youtube.com/",
        "clicks": 50
    },
    ...
]`
