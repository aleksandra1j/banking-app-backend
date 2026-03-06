# Banking System API

A RESTful Banking API built with Java and Spring Boot that allows users to manage accounts and perform financial transactions.

## Tech Stack
- Java
- Spring Boot
- Spring Security
- Maven
- REST APIs

## Features
- User account creation
- Deposit and withdrawal operations
- Money transfer between accounts
- Transaction history

## Project Structure
controller – REST API endpoints  
service – Business logic  
repository – Database access  
dto – Request/response models  
model – domain entities  
config – security configuration  
utils – helper utilities

## Example Endpoint
GET /api/transactions/statement
Returns a bank statement for a given account and date range.
