# Spring Boot Project simulating a payment api integrated with multiple mock payment service providers
## (This an  ongoing Project but deployable :) )
This is the improved version of the  <a href=https://github.com/OzgurYatmaz/FirisbeInterview>following</a> project. <br>
In this version it is possible to send payment requests to many external payment service providers. 

### How to run the project

1. Build a Jar with maven install command to /target/ folder 
2. Copy the jar to desired location
2. Run the jar with comand: java -jar "jar-name".jar

 
### How to test api
1. Use the Mockoon collection provided to mock sample external payment service providers

2. Use the url below to test with swagger <br>
   http://localhost:8080/swagger-ui/index.html
 

### Tech Stack:

Language: Java 17 <br>
Framework: Spring Boot 3.2.5 <br>
Database: MySql <br>
DB Management: Spring Data JPA <br>
Unit Tests: JUnit and Maven Surefire for functionality coverage test reports <br>
Documentation: Swagger 3 (OPenAPI)  <br>
Build Tool: Maven 
