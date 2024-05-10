/**
 * This package is for all main services of the integrated payment service providers
 */
package com.multipay.routing.dispatchers;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;

/**
 * Skeleton of the Payment service clients for consuming external payment service providers
 */
public interface MultiPayDispatcher {
	
	public ProcessPaymentResponseDTO startPayment(ProcessPaymentRequestDTO multipayRequest);
	

	public int getDispatcherId();
	
	public void setDispatcherId(int dispatcherId);

	
	
}