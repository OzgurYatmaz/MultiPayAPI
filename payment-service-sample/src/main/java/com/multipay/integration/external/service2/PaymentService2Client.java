package com.multipay.integration.external.service2;

import org.springframework.stereotype.Component;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.entity.Card;
import com.multipay.entity.TechnicalException;

@Component
public class PaymentService2Client {

	
	 

	public ProcessPaymentResponseDTO processPayment(ProcessPaymentRequestDTO startPaymentRequest, Card card) throws TechnicalException{
		// TODO Auto-generated method stub
		return null;
	}
}
