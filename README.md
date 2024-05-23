# Spring Boot Project Simulating a Payment API Integrated with Multiple Mock Payment Service Providers
## (This project need to be converted to multi-modular structe and currently thankfully I don't have resources to do it)

This is the improved version of the [following](https://github.com/OzgurYatmaz/FirisbeInterview) project. 

In this version, it is possible to send payment requests to many external payment service providers. 

- Currently, there are only 2 external service samples integrated (providerId=1 and providerId=2), and there is no limit in adding external payment service providers.
- I will convert this project to a multi-modular structure soon.

Online Swagger document is published [here](https://app.swaggerhub.com/apis-docs/ozguryatmaz/multi-pay_api/1.0.0).

### How to Run the Project

1. Build a Jar with maven install command to `/target/` folder.
2. Copy the jar to the desired location.
3. Run the jar with command: `java -jar payment-service-sample-1.0.0.jar`.

### How to Test API

1. Create schema in MySQL database with the following command: `CREATE SCHEMA 'multipay'`.
2. Use the Mockoon collection provided [here](API-Documents/Mockoon%20Collection%20for%20Mock%20Service).
3. Use the URL below to test with Swagger: http://localhost:8080/swagger-ui/index.html 

### Tech Stack:

- Language: Java 17
- Framework: Spring Boot 3.2.5
- Database: MySQL
- DB Management: Spring Data JPA
- Unit Tests: JUnit and Maven Surefire for functionality coverage test reports
- Documentation: Swagger 3 (OpenAPI)
- Build Tool: Maven
