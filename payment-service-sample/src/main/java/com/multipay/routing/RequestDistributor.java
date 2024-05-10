/**
 * This package contains classes for routing functionality
 * .
 */
package com.multipay.routing;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.entity.Card;
import com.multipay.routing.dispatchers.MultiPayDispatcher;
import com.multipay.routing.dispatchers.PaymentService1;
import com.multipay.routing.dispatchers.PaymentService2;
import com.multipay.utils.MessageEnums;
import com.multipay.utils.ResponseUtils;

/**
 * 
 * This is for routing the request to selected payment service
 * provider based on the providerId.
 * 
 * 
 */
@Service
public class RequestDistributor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestDistributor.class);

	/**
	 * 
	 * For setting response details
	 * 
	 */
	@Autowired
	private ResponseUtils responseUtils;


	/**
	 * 
	 * For fetching beans from spring container.
	 * 
	 */
	@Autowired
	private ApplicationContext context;
	
	/**
	 * 
	 * For registering the integrated payment services
	 * 
	 */
	public List<MultiPayDispatcher> dispatchers;


	/**
	 * 
	 * For registering the integrated payment services during project start
	 * 
	 */
	@PostConstruct
	public void init() {
		dispatchers = new ArrayList<MultiPayDispatcher>();

		MultiPayDispatcher dispatcher1 = (MultiPayDispatcher) context.getBean(PaymentService1.class);
		dispatcher1.setDispatcherId(1);
		dispatchers.add(dispatcher1);

		MultiPayDispatcher dispatcher2 = (MultiPayDispatcher) context.getBean(PaymentService2.class);
		dispatcher2.setDispatcherId(2);
		dispatchers.add(dispatcher2);

		LOGGER.info("2 dispatchers successfully initalized.");

	}


	/**
	 * 
	 * Fetches payment service based on the providerId supplied in payment request
	 * 
	 * @param providerId
	 * @return Child class objects of the MultiPay dispatcher which functions as clients of external payment service providers
	 * 
	 * 
	 */
	private MultiPayDispatcher getDispatcher(int dispatcherId) {
		for (MultiPayDispatcher d : dispatchers) {
			if (d.getDispatcherId() == dispatcherId) {
				return d;
			}
		}
		return null;
	}



	/**
	 * 
	 * Fetches payment service based on the providerId supplied in payment request
	 * 
	 * @param Payment request object
	 * 
	 * @return Payment response object includes info if payment is successful or
	 *         failed.
	 * 
	 */
	public ProcessPaymentResponseDTO startPayment(ProcessPaymentRequestDTO paymentRequest) {

		MultiPayDispatcher selectedDispatcher = getDispatcher(paymentRequest.getProviderId());

		if (selectedDispatcher == null) {
			ProcessPaymentResponseDTO response = new ProcessPaymentResponseDTO();
			responseUtils.setStatusAsFailed(response, MessageEnums.INVALID_PROVIDER_ID.getMessageCode(), null,
					paymentRequest.getProviderId());
			return response;
		}

		return selectedDispatcher.startPayment(paymentRequest);

	}

}
