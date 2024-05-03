package com.multipay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.multipay.beans.MessageEnums;
import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.beans.Response;
import com.multipay.beans.ResponseHeader;
import com.multipay.customer.service.error.ErrorDetails;
import com.multipay.model.Card;
import com.multipay.model.TechnicalException;
import com.multipay.repository.CardRepository;
import com.multipay.routing.RequestDistributor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/multipay/rest/payment")
@Tag(name = "Transaction controller", description = "Make  and query payments") // For Swagger
@RestController
public class MultiPayRestService {


	@Autowired
	MessageSource messageSource;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	RequestDistributor distributorBean;

	@RequestMapping(value = "/startPayment", method = RequestMethod.POST)
	@Operation(summary = "Start Payment", description = "Sends request to external payment services")
//	@ApiResponses({
//		@ApiResponse(responseCode = "201", description = "When customer is successfully saved to data base"),
//		@ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(hidden = true))} ),
//		@ApiResponse(responseCode = "409", description = "When submitted data is in conflict with existing data in database or with itself", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
//	  })
	public ProcessPaymentResponse startPayment(@RequestBody ProcessPaymentRequest paymentRequest) {

		ProcessPaymentResponse response = new ProcessPaymentResponse();
		if (!cardRepository.existsByCardNumber(paymentRequest.getCardNumber())) {
			setStatusAsFailed(response, MessageEnums.INVALID_CARD_NUMBER.getWsCode(), null,
					paymentRequest.getProviderId());
			return response;
		}

		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);

		if (card.getBalance() < paymentRequest.getAmount()) {
			setStatusAsFailed(response, MessageEnums.LIMIT_EXCEED.getWsCode(), null, paymentRequest.getProviderId());
			return response;
		}
		response = distributorBean.startPayment(paymentRequest, card);

		return response;
	}

	public void setStatusAsFailed(Response response, String error, Object[] args, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setCode(error);
		response.getResponseHeader().setDescription(messageSource.getMessage(error, args, "Default message", null));
		response.getResponseHeader().setSuccessful(false);
		response.setProviderId(providerId);
	}

	private void createResponseHeader(Response response) {
		if (response.getResponseHeader() == null) {
			ResponseHeader responseHeader = new ResponseHeader();

			response.setResponseHeader(responseHeader);
		}
	}
}
