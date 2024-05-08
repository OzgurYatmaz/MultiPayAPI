package com.multipay.routing.dispatchers;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.model.Card;

public interface MultiPayDispatcher {
	
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest multipayRequest, Card card);
	

	public int getDispatcherId();
	
	public void setDispatcherId(int dispatcherId);

	
	
}