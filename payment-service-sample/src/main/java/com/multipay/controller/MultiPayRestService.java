/**
 * This package contains classes for all controllers of the project
 * .
 */
package com.multipay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.entity.Card;
import com.multipay.repository.CardRepository;
import com.multipay.routing.RequestDistributor;
import com.multipay.utils.MessageEnums;
import com.multipay.utils.ResponseUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * This is main controller for processing payment from selected payment service
 * provider.
 * 
 * 
 */
@RequestMapping("/multipay/payments")
@Tag(name = "Transaction controller", description = "Make  payments") // For Swagger
@RestController
public class MultiPayRestService {

	/**
	 * 
	 * For setting response details
	 * 
	 */
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
	@Operation(summary = "Makes Payment from the balance of the card", description = "Makes payment from balance of the card by using the selected external payment service provider and records payment info to database")
	public ProcessPaymentResponseDTO startPayment(@RequestBody ProcessPaymentRequestDTO paymentRequest) {

		long startTime = System.currentTimeMillis(), finishTime = 0;

		ProcessPaymentResponseDTO response = new ProcessPaymentResponseDTO();
		if (!cardRepository.existsByCardNumber(paymentRequest.getCardNumber())) {
			responseUtils.setStatusAsFailed(response, MessageEnums.CARD_NOT_EXIST.getMessageCode(), null,
					paymentRequest.getProviderId());
			finishTime = System.currentTimeMillis();
			responseUtils.setResponseTime(response, startTime, finishTime);
			return response;
		}

		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);

		if (card.getBalance() < paymentRequest.getAmount()) {

			responseUtils.setStatusAsFailed(response, MessageEnums.LIMIT_EXCEED.getMessageCode(), null,
					paymentRequest.getProviderId());
			finishTime = System.currentTimeMillis();
			responseUtils.setResponseTime(response, startTime, finishTime);
			return response;
		}
		response = distributorBean.startPayment(paymentRequest, card);

		finishTime = System.currentTimeMillis();
		responseUtils.setResponseTime(response, startTime, finishTime);
		return response;
	}

}
