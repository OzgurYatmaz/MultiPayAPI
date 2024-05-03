package com.multipay.routing.dispatchers;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;

public interface MultiPayDispatcher {
	
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest multipayRequest);
	

	public int getDispatcherId();
	
	public void setDispatcherId(int dispatcherId);

	
	
}