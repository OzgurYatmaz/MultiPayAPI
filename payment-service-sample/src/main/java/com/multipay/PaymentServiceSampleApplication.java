package com.multipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * For api documentaion and testing use the url below:
 * http://localhost:8080/swagger-ui/index.html
 * 
 * For web document use the link below:
 * https://app.swaggerhub.com/apis-docs/ozguryatmaz/secure-pay_api_for_firisbe_interview/1.0
 */

@SpringBootApplication
public class PaymentServiceSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceSampleApplication.class, args);
	}

}
