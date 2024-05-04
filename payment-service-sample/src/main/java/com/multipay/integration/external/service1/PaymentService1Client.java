package com.multipay.integration.external.service1;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipay.beans.MessageEnums;
import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.beans.ResponseHeader;
import com.multipay.configuration.PaymentServiceConfig;
import com.multipay.model.Card;
import com.multipay.model.Payment;
import com.multipay.model.TechnicalException;
import com.multipay.repository.CardRepository;
import com.multipay.repository.PaymentRepository;

@Component
public class PaymentService1Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService1Client.class);

	@Autowired
	private PaymentServiceConfig configParameters;
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	private ObjectMapper objectMapper = null;

	public PaymentService1Client() {
		objectMapper = new ObjectMapper();
	}

	public ProcessPaymentResponse processPayment(ProcessPaymentRequest paymentRequest, Card card) throws TechnicalException {
		// Fetch the card by cardNumber
//		if (!cardRepository.existsByCardNumber(paymentRequest.getCardNumber())) {
//			throw new TechnicalException(MessageEnums.INVALID_CARD_NUMBER.getWsCode(),
//					"Card number " + paymentRequest.getCardNumber() + " is not associated with any customer");
//		}
//
//		Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).get(0);
//
//		if (card.getBalance() < paymentRequest.getAmount()) {
//			throw new TechnicalException(MessageEnums.LIMIT_EXCEED.getWsCode(), "Insuffucient balance",
//					"Insuffucient balance2");
//		}
		// create an object for external service's request body. This is just dummy
		Payment payment = prepareExternalRequest(paymentRequest, card);

		ResponseEntity<String> responseEntity = null;
		ProcessPaymentResponse response;
		try {

			// Call the external payment service
			long startTime = System.currentTimeMillis();
			responseEntity = sendPaymentRequestToExternalService(paymentRequest);
			long finishTime = System.currentTimeMillis();
			response = processExternalResponse(payment, responseEntity, card.getId(), finishTime - startTime);

		}catch (TechnicalException ex) {

			LOGGER.error("Error occurred while accessing external payment service", ex);
			throw ex;

		}catch (Exception ex) {

			LOGGER.error("Error occurred while accessing external payment service", ex);
			throw new TechnicalException(MessageEnums.COMMON_EXTERNAL_SERVICE_ERROR.getWsCode(), ex.getMessage());

		}

		return response;
	}

	private ResponseEntity<String> sendPaymentRequestToExternalService(ProcessPaymentRequest externalRequest)
			throws  TechnicalException, JsonProcessingException {

		String jsonRequest = objectMapper.writeValueAsString(externalRequest);

		if (LOGGER.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer();

			buffer.append("\n\t").append("sendPaymentRequestToExternalService method ->").append("\n\t")
					.append("Json Request -> ").append(jsonRequest).append("\n");

			LOGGER.debug(buffer.toString());
		}
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofMillis(configParameters.getTimeout()))
				.setReadTimeout(Duration.ofMillis(configParameters.getTimeout())).build();

		try {

			ResponseEntity<String> externalResponse = restTemplate
					.postForEntity(configParameters.getPaymentserviceurl(), externalRequest, String.class);
			if (LOGGER.isDebugEnabled()) {
				StringBuffer bufferResponse = new StringBuffer();

				bufferResponse.append("MultiPay ExternalService1 -> sendPaymentRequestToExternalService response.")
						.append("\n\t").append("ResponseCode :").append(externalResponse.getStatusCode()).append("\n\t")
						.append("External Service1 response :").append(externalResponse.getBody()).append("\n\t");

				LOGGER.debug(bufferResponse.toString());
			}
			return externalResponse;
		} catch (Exception ex) {
			LOGGER.error("Error while sending request to external payment service provider: ", ex);
			throw new TechnicalException(MessageEnums.ERROR_SENDING_REQUEST.getWsCode(), ex.getMessage());
		}
	}

	private Payment prepareExternalRequest(ProcessPaymentRequest paymentRequest, Card card) {
		Payment payment = new Payment();
		payment.setCardNumber(card.getCardNumber());
		payment.setAmount(paymentRequest.getAmount());
		LocalDateTime paymentTime = LocalDateTime.now();
		payment.setPaymentDate(paymentTime);
		payment.setCustomerNumber(card.getCustomerNumber());
		payment.setPaymentProvider("SERVICE-1");
		return payment;
	}

	private ProcessPaymentResponse processExternalResponse(Payment payment, ResponseEntity<String> responseEntity,
			Integer cardId, long responseTime) {

		ProcessPaymentResponse response = new ProcessPaymentResponse();
		ResponseHeader header = new ResponseHeader();
		if (ObjectUtils.isNotEmpty(responseEntity)) {
			// additional confirmation steps might be needed depending on the requirements
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				// only successful payments are saved to DB for now.
				paymentRepository.save(payment);
				cardRepository.updateBalanceAfterPayment(cardId, payment.getAmount());

				header.setCode("00");
			} else {
				LOGGER.error("Payment response received indicates the failure of payment: ");
				header.setCode("99");
			}
		} else {
			header.setCode("98");
		}
		response.setResponseHeader(header);
		response.setExternalExecutionTime(responseTime);
		return response;
	}
}
