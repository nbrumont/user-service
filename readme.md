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
- an editor or IDE

### Compiling sources and running the server

1. Clone this repo using ssh or https
2. Install dependencies and run tests with maven:
   <code>mvn clean install</code>
3. Start a dev server : <code>mvn spring-boot:run</code> or a prod server <code>java -jar
   target/user-service-0.0.1.jar</code>
4. Database administration console is available at http://localhost:8080/users/h2-console (jdbc url is jdbc:h2:mem:user,
   the rest is default)

### Using the API

A user :

- must have a username
- must have a birthDate (YYYY-MM-DD) and should be at least 18 years old
- must have a residenceCountry set to "France" or "FRA" (case is not important)
- can have a phoneNumber. If he does, it should start by 0 or +33, followed by 9 numbers
- can have a gender.

Once the server is started, you can use the 2 following API :

- GET http://localhost:8080/users/{id} to retrieve a user by his technical id.
- POST http://localhost:8080/users/ to create a new user

see [postman-collection](./postman_collection.json)
or [MVC tests](./src/test/java/fr/nbrumont/user/api/UserControllerIntegrationTest.java) to learn more on how to use
these.

### Configuration

This configuration is done inside [application.properties](./src/main/resources/application.properties) :

- Root context is /users
- Database is h2 and url is jdbc:h2:mem:user
- Database console is enabled on /h2-console
- Hibernate creates schema at startup
- No data is initialized during schema creation

### Testing strategy

1. Code containing any logic is tested against unit test and MVC api tests.
2. Code not containing any logic is tested against MVC api tests.

Overall coverage is 95%. Missed hits are :

- on ClockConfig (Clock is always mocked)
- on unreachable code : auto-generated mapper that checks null on user input
- on UserApplication (Spring boot entry point)
