package com.multipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * This is the starting point of the project. Run this as java application to
 * run the project
 * 
 * For API documentation and testing use the url below:
 * http://localhost:8080/swagger-ui/index.html
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-11
 * 
 */

@SpringBootApplication
public class PaymentServiceSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceSampleApplication.class, args);
	}

}
