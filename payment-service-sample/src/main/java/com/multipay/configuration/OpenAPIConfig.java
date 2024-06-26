/**
 * This package contains classes for project configurations.
 */
package com.multipay.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger configuration is made from here.
 * 
 * Use the link {@link http://localhost:8080/swagger-ui/index.html} to see
 * swagger page of the project.
 * 
 * OpenApi is new name for Swagger 3
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */
@Configuration
public class OpenAPIConfig {

	@Value("${openapi.dev-url}")
	private String devUrl;

	@Value("${openapi.prod-url}")
	private String prodUrl;

	/**
	 * 
	 * Creates bean of swagger UI object for swagger page of the project
	 * 
	 * @return OpenApi object to be used by swagger api internally
	 * 
	 */
	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("ozguryatmaz@yandex.com");
		contact.setName("Ozgur Yatmaz");
		contact.setUrl("https://www.linkedin.com/in/ozguryatmaz");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("MultiPay API").version("1.0.0").contact(contact).description(
				"This API is a sample payment service integrated to multiple external payment service providers "
				+ " (Two sample service is integrated currently but there is not limit). Service anables saving customers and cards of"
				+ " the customers to mysql database and requesting payments to selected (selection is made with providerId field and "
				+ "currenly 1 and 2 are selectable) external payment service providers. And if external payment service"
						+ " confirms that the payment is made card balance is updated and payment is recorded to payments table."
						+ "     <br /> Lastly, all payments can be queried by date interval or curtomer number or card number.<br />\r\n"
						+ "     <br /> Tech Stack:\r\n" + "     <br />\r\n" 
						+ "     <br /> Language: Java 17\r\n"
						+ "     <br /> Framework: Spring Boot 3.2.5\r\n" 
						+ "     <br /> Database: MySql\r\n"
						+ "     <br /> DB Management: Spring Data JPA\r\n"
						+ "     <br /> Unit Tests: JUnit and Maven Surefire for test reports\r\n"
						+ "     <br /> Documentation: Swagger 3 - (OpenAPI)\r\n"
						+ "     <br /> Build Tool: Maven")
				.license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}
}