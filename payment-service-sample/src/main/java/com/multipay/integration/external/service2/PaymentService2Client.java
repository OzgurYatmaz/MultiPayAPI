package com.multipay.integration.external.service2;

import org.springframework.stereotype.Component;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.model.Card;
import com.multipay.model.TechnicalException;

@Component
public class PaymentService2Client {

	
	 

	public ProcessPaymentResponse processPayment(ProcessPaymentRequest startPaymentRequest, Card card) throws TechnicalException{
		// TODO Auto-generated method stub
		return null;
	}
}
