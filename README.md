# Banking System API

A RESTful Banking API built with Java and Spring Boot that allows users to create accounts, perform transactions, and retrieve transaction history.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- REST APIs
- Postman (API testing)

## Features

- Account creation
- Deposit and withdrawal operations
- Money transfer between accounts
- Transaction history / bank statements
- Database persistence using PostgreSQL

## Project Structure

controller – REST API endpoints  
service – Business logic layer  
repository – Database interaction using Spring Data JPA  
dto – Request and response objects  
model – Entity classes  
config – Application configuration  
utils – Helper classes

## API Testing

All API endpoints were tested using Postman.

## Example Endpoint
GET /api/transactions/statement
-Returns a bank statement for a given account and date range.
