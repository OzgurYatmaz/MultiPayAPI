package com.multipay.routing;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.routing.dispatchers.MultiPayDispatcher;
import com.multipay.routing.dispatchers.PaymentService1;
import com.multipay.routing.dispatchers.PaymentService2;

 
@Service
public class RequestDistributor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestDistributor.class);
	
	@Autowired
	private ApplicationContext context;
	
	public List<MultiPayDispatcher> dispatchers;
	
	
	
	
	@PostConstruct
	public void init(){
		dispatchers=new ArrayList<MultiPayDispatcher>();
		
		MultiPayDispatcher dispatcher1=(PaymentService1) context.getBean(PaymentService1.class);
		dispatcher1.setDispatcherId(1);
		dispatchers.add(dispatcher1);
		
		MultiPayDispatcher dispatcher2=(MultiPayDispatcher) context.getBean(PaymentService2.class);
		dispatcher2.setDispatcherId(2);
		dispatchers.add(dispatcher2);
		
		LOGGER.info("2 dispatchers successfully initalized.");
		
	}
	
	private MultiPayDispatcher getDispatcher(int dispatcherId) {
		for (MultiPayDispatcher d : dispatchers) {
				if (d.getDispatcherId() == dispatcherId) {
						return d;
				}
		}
		return null;
	}

	
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest startPaymentRequest) {
		return getDispatcher(startPaymentRequest.getProviderId()).startPayment(startPaymentRequest);
	}

	 
}
