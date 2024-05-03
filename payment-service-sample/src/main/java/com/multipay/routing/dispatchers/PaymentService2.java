package com.multipay.routing.dispatchers;
import org.springframework.stereotype.Component;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;

@Component
public class PaymentService2 extends AbstractDispatcher {

	@Override
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest multipayRequest) {
		return null;
	}

 
 

 

}
