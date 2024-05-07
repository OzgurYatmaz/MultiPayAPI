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
import com.multipay.model.Card;
import com.multipay.repository.CardRepository;
import com.multipay.routing.RequestDistributor;
import com.multipay.utils.ResponseUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This is main controller for processing payment from selected payment service
 * provider.
 * 
 * @param Payment request object includes card number to associate payment to
 *                card and payment amount and id of the external payment
 *                provider.
 * @return Payment response object includes info payment status and response
 *         times of external payment service provider and total response time of
 *         this web service.
 * 
 * 
 **/

@RequestMapping("/multipay/rest/payment")
@Tag(name = "Transaction controller", description = "Make  and query payments") // For Swagger
@RestController
public class MultiPayRestService {

	@Autowired
	private ResponseUtils responseUtils;
	/**
	 * 
	 * Card related operations is done with this
	 * 
	 */
	@Autowired
	private CardRepository cardRepository;

	/**
	 * 
	 * Required for routing the payment request to selected external payment service
	 * provider.
	 * 
	 */
	@Autowired
	RequestDistributor distributorBean;

	/**
	 * Make payment from registered card in data base by using the selected external
	 * payment service provider. Initially card number and card balance is checked
	 * (if card is exist in database and balance is sufficient). if validation is
	 * successful payment request is directed to selected external payment service
	 * provider to make the payment.
	 * 
	 * @param paymentRequest object includes card number to associate payment to
	 *                       card and payment amount and providerId corresponding
	 *                       external payment service provider.
	 * @return Payment response object includes info if payment is successful or
	 *         failed.
	 * 
	 * 
	 */
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
			responseUtils.setStatusAsFailed(response, MessageEnums.INVALID_CARD_NUMBER.getWsCode(), null,
					paymentRequest.getProviderId());
			return response;
		}

		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);

		if (card.getBalance() < paymentRequest.getAmount()) {
			responseUtils.setStatusAsFailed(response, MessageEnums.LIMIT_EXCEED.getWsCode(), null,
					paymentRequest.getProviderId());
			return response;
		}
		response = distributorBean.startPayment(paymentRequest, card);

		return response;
	}

}
