package com.multipay.integration.external.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentService2ClientFactory {

	
	private PaymentService2RequestFactory requestFactory;
	private PaymentService2ResponseFactory responseFactory;
	
	@Autowired
	public PaymentService2ClientFactory() {
		this.requestFactory = new PaymentService2RequestFactory();
		this.responseFactory = new PaymentService2ResponseFactory();
	}
}
