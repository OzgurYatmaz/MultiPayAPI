package com.multipay.routing.dispatchers;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.entity.Card;

public interface MultiPayDispatcher {
	
	public ProcessPaymentResponseDTO startPayment(ProcessPaymentRequestDTO multipayRequest, Card card);
	

	public int getDispatcherId();
	
	public void setDispatcherId(int dispatcherId);

	
	
}