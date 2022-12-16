# User service

## Presentation

This app is a simple API allowing the creation and reading of users

This app uses the following technologies and tools

- Spring boot with java 17
- H2 in-memory database for both runtime and test purposes

## How to use

Prerequisites :

- Java 17
- Maven
- Git
- A way to send http requests (curl, postman ...)

1. Clone this repo using ssh or https
2. Install dependancies and run tests with maven:
   <code>mvn clean install</code>
3. Start a dev server : <code>mvn spring-boot:run</code> or a prod server <code>java -jar
   target/user-service-0.0.1.jar</code>

