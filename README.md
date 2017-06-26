# Reserva de salas

API RESTful e aplicação Angular 2 para um sistema de reserva de salas

## Requisitos

* Apache Maven 3.3.9
* Java 8
* Node.js (https://nodejs.org/en/download/package-manager/)
 
## Diretório da API RESTful em Java:
  * reserva-salas/reserva-salas-api

## Diretório da aplicação Angular 2:
  * reserva-salas/reserva-salas-app

## Como rodar o projeto

* Abra um terminal e clone o projeto:
* git clone https://github.com/vagneroliveirars/reserva-salas.git
* Entre no diretório da API:
  * cd reserva-salas/reserva-salas-api
* Execute o comando mvn wildfly:run
* Confira os logs

* Abra um novo terminal e entre no diretório da aplicação Angular 2:
  * cd reserva-salas/reserva-salas-app
* Execute o comando npm install
* Execute o comando npm start
* A aplicação estará disponível no endereço: http://localhost:3000/reserva

## Funcionamento

* O plugin wildfly do Maven roda o application server (WildFly embarcado) e faz deploy da aplicação
* O download do WildFly será feito automaticamente pelo Maven
* A aplicação utiliza o banco de dados h2 em modo embarcado e em memória. 
* As tabelas são geradas automaticamente pelo Hibernate. 
* Dados de locais e salas são previamente importados na criação das tabelas.

# Endpoints

Todos os endpoints são RESTful com Content-Type: application/json.

URL base: http://localhost:8080/reserva-salas

## Serviço REST para reservas

### GET http://localhost:8080/reserva-salas/reservas/

Lista todas as reservas

### GET http://localhost:8080/reserva-salas/reservas/:id

Busca a reserva por id.

Caso o id não exista no sistema, retorna um 404 Not Found

### POST http://localhost:8080/reserva-salas/reservas

Cadastra uma nova reserva

Sucesso: retorna um 201 Created

Caso exista um choque de horários na mesma sala e local retorna um 409 Conflict com a mensagem de erro

### PUT http://localhost:8080/reserva-salas/reservas

Atualiza uma reserva

Sucesso: retorna um 204 No Content

Caso o id não exista no sistema, retorna um 404 Not Found

Caso exista um choque de horários na mesma sala e local retorna um 409 Conflict com a mensagem de erro

### DELETE http://localhost:8080/reserva-salas/reservas/:id

Deleta uma reserva

Sucesso: retorna um 204 No Content

Caso o id não exista no sistema, retorna um 404 Not Found

## Serviço REST para locais

### GET http://localhost:8080/reserva-salas/locais/

Lista todos os locais

### GET http://localhost:8080/reserva-salas/locais/:id/salas

Lista as salas por local

Caso o local informado não possua salas cadastradas, retorna um 404 Not Found
