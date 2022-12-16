# User service

## Presentation

This app is a simple API allowing the creation and reading of users

This app uses the following technologies and tools

- Spring boot with java 17
- H2 in-memory database for both runtime and test purposes

## How to use

### Prerequisites :

- Java 17
- Maven
- Git
- A way to send http requests (curl, postman ...)

### Compiling sources and running the server

1. Clone this repo using ssh or https
2. Install dependencies and run tests with maven:
   <code>mvn clean install</code>
3. Start a dev server : <code>mvn spring-boot:run</code> or a prod server <code>java -jar
   target/user-service-0.0.1.jar</code>

### Using the API

A user :

- must have a username
- must have a birthDate (YYYY-MM-DD) and should be at least 18 years old
- must have a residenceCountry set to "France" or "FRA" (case is not important)
- can have a phoneNumber. If he does, it should start by 0 or +33, following by 9 numbers
- can have a gender.

Once the server is started, you can use the 2 following API :

- GET http://localhost:8080/users/{id} to retrieve a user by his technical id.
- POST http://localhost:8080/users/ to create a new user

see [postman-collection](./postman_collection.json)
or [integration tests](./src/test/java/fr/nbrumont/user/api/UserControllerTest.java) to learn more on how to use these.

