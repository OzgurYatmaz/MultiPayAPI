# Spring Boot Project simulating a payment api integrated with multiple mock payment service providers
## (This an  ongoing Project but deployable :) )
This is the improved version of the  <a href=https://github.com/OzgurYatmaz/FirisbeInterview>following</a> project. <br>
In this version it is possible to send payment requests to many external payment service providers. <br><br>
 -Currently there are only 2 external service samples integrated (providerId=1 and providerId=2) and there is no limit of adding any number of external payment services.<br>
 -After this project is completed I will convert it to multi modular structure.

### How to run the project

1. Build a Jar with maven install command to /target/ folder 
2. Copy the jar to desired location
2. Run the jar with comand: java -jar "jar-name".jar

 
### How to test api
1. Create scheama in mysql database with following command "CREATE SCHEMA `multipay' "
2. Use the Mockoon collection provided <a href=API-Documents>here</a>
3. Use the url below to test with swagger <br>
   http://localhost:8080/swagger-ui/index.html
 

### Tech Stack:

Language: Java 17 <br>
Framework: Spring Boot 3.2.5 <br>
Database: MySql <br>
DB Management: Spring Data JPA <br>
Unit Tests: JUnit and Maven Surefire for functionality coverage test reports <br>
Documentation: Swagger 3 (OPenAPI)  <br>
Build Tool: Maven 
