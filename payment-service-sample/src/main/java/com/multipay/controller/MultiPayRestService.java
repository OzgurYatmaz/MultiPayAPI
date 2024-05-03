package com.multipay.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.error.ErrorDetails;
import com.multipay.routing.RequestDistributor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/multipay/rest/payment")
@Tag(name = "Payment controller", description = "Make  and query payments") // For Swagger
@RestController
public class MultiPayRestService {
	
	
	@Autowired
	RequestDistributor distributorBean;
	
	@RequestMapping(value = "/startPayment", method = RequestMethod.POST)
	@Operation(summary = "Start Payment", description = "Sends request to external payment services")
//	@ApiResponses({
//		@ApiResponse(responseCode = "201", description = "When customer is successfully saved to data base"),
//		@ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(hidden = true))} ),
//		@ApiResponse(responseCode = "409", description = "When submitted data is in conflict with existing data in database or with itself", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
//	  })
	public ProcessPaymentResponse startPayment(@RequestBody ProcessPaymentRequest startPaymentRequest) {

		ProcessPaymentResponse response = distributorBean.startPayment(startPaymentRequest);

		return response;
	}
	
	 

}
