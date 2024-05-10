/**
 * This package is part of integration layer and for sample client1
 */
package com.multipay.integration.external.service1;

import java.time.Duration;
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
import com.multipay.configuration.PaymentServiceParameters;
import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.dto.ResponseHeader;
import com.multipay.entity.Card;
import com.multipay.entity.TechnicalException;
import com.multipay.integration.external.service1.dto.ExternalService1PaymentResponseDTO;
import com.multipay.utils.MessageEnums;

/**
 * 
 * Class responsible to communicate external payment service provider 1
 * 
 * @throws Various exceptions explaining the reasons of failures.
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-10
 * 
 */
@Component
public class PaymentService1Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService1Client.class);

	@Autowired
	private PaymentServiceParameters configParameters;


	private ObjectMapper objectMapper = null;

	public PaymentService1Client() {
		objectMapper = new ObjectMapper();
	}

	public ProcessPaymentResponseDTO processPayment(ProcessPaymentRequestDTO paymentRequest, Card card) throws TechnicalException {
		
		ResponseEntity<ExternalService1PaymentResponseDTO> responseEntity = null;
		ProcessPaymentResponseDTO response;
		try {

			// Call the external payment service
			long startTime = System.currentTimeMillis();
			responseEntity = sendPaymentRequestToExternalService(paymentRequest);
			long finishTime = System.currentTimeMillis();
			response = processExternalResponse(responseEntity, finishTime - startTime);

		}catch (TechnicalException ex) {

			LOGGER.error("Error occurred while accessing external payment service", ex);
			throw ex;

		}catch (Exception ex) {

			LOGGER.error("Error occurred while accessing external payment service", ex);
			throw new TechnicalException(MessageEnums.COMMON_EXTERNAL_SERVICE_ERROR.getWsCode(), ex.getMessage());

		}

		return response;
	}

	private ResponseEntity<ExternalService1PaymentResponseDTO> sendPaymentRequestToExternalService(ProcessPaymentRequestDTO externalRequest)
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

			ResponseEntity<ExternalService1PaymentResponseDTO> externalResponse = restTemplate
					.postForEntity(configParameters.getPaymentserviceurl(), externalRequest, ExternalService1PaymentResponseDTO.class);
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

	private ProcessPaymentResponseDTO processExternalResponse(ResponseEntity<ExternalService1PaymentResponseDTO> responseEntity,
			 long responseTime) throws TechnicalException {

		ProcessPaymentResponseDTO response = new ProcessPaymentResponseDTO();
		ResponseHeader header = new ResponseHeader();
		if (ObjectUtils.isNotEmpty(responseEntity) && ObjectUtils.isNotEmpty(responseEntity.getBody())) {
			 
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				header.setSuccessful(true);
			} else {
				LOGGER.error("Payment response received indicates the failure of payment: ");
				header.setDescription(responseEntity.getBody().getStatus());
				header.setCode(responseEntity.getBody().getCode()+"");
				header.setSuccessful(false);
			}
		} else {
			throw new TechnicalException(MessageEnums.OPERATION_RESPONSE_NULL.getWsCode(), "External service error response is null");
		}
		response.setResponseHeader(header);
		response.setExternalExecutionTime(responseTime);
		return response;
	}
}
