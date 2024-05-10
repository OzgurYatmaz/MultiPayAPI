/**
 * This package is part of integration layer for sample external payment service provider 1
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
import com.multipay.entity.TechnicalException;
import com.multipay.integration.external.service1.dto.ExternalService1PaymentRequestDTO;
import com.multipay.integration.external.service1.dto.ExternalService1PaymentResponseDTO;
import com.multipay.utils.MessageEnums;

/**
 * 
 * Class responsible to communicate with external payment service provider 1
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

	/**
	 * 
	 * To read configuration parameters from .properties file
	 * 
	 * @see com.multipay.configuration.PaymentServiceParameters class
	 * @see /src/main/resources/application-dev.properties
	 * 
	 */
	@Autowired
	private PaymentServiceParameters configParameters;

	
	/**
	 * 
	 * For monitoring the communication between this API and external service providers
	 * 
	 */
	private ObjectMapper objectMapper = null;

	public PaymentService1Client() {
		objectMapper = new ObjectMapper();
	}

	/**
	 * 
	 * Processes payment by using external payment service provider
	 * 
	 * @throws TechnicalException if failure occurs during process
	 * 
	 */
	public ProcessPaymentResponseDTO processPayment(ProcessPaymentRequestDTO paymentRequest) throws TechnicalException {
		
		ResponseEntity<ExternalService1PaymentResponseDTO> responseEntity = null;
		ProcessPaymentResponseDTO response;
		ExternalService1PaymentRequestDTO externalRequest = prepareExternalRequest(paymentRequest);
		try {

			// Call the external payment service
			long startTime = System.currentTimeMillis();
			responseEntity = sendPaymentRequestToExternalService(externalRequest);
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

	/**
	 * 
	 * Convert the Request DTO of this API to required request body object of the external service
	 * 
	 * @param Request DTO of this API
	 * 
	 * @return Required request body object of the external service.
	 * 
	 * 
	 */
	private ExternalService1PaymentRequestDTO prepareExternalRequest(ProcessPaymentRequestDTO paymentRequest) {

		ExternalService1PaymentRequestDTO externalRequest = new ExternalService1PaymentRequestDTO();
		externalRequest.setAmount(paymentRequest.getAmount());
		// so on and so forth depending on the external service requirements
		return externalRequest;
	}

	/**
	 * 
	 * Sends payment request to external payment service provider.
	 * 
	 * @param payment request object to carry payment data.
	 * 
	 * @throws TechnicalException if failure occurs during external service call.
	 * 
	 */
	private ResponseEntity<ExternalService1PaymentResponseDTO> sendPaymentRequestToExternalService(ExternalService1PaymentRequestDTO externalRequest)
			throws  TechnicalException, JsonProcessingException {


		if (LOGGER.isDebugEnabled()) {
			String jsonRequest = objectMapper.writeValueAsString(externalRequest);
			
			StringBuffer buffer = new StringBuffer();

			buffer.append("\n\t").append("Request send to external Paymenr service 1: ->").append("\n\t")
					.append("Json Request -> ").append(jsonRequest).append("\n\t")
					.append("To url: -> ").append(configParameters.getPaymentservice1url());

			LOGGER.debug(buffer.toString());
		}
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofMillis(configParameters.getTimeout()))
				.setReadTimeout(Duration.ofMillis(configParameters.getTimeout())).build();

		try {

			ResponseEntity<ExternalService1PaymentResponseDTO> externalResponse = restTemplate
					.postForEntity(configParameters.getPaymentservice1url(), externalRequest, ExternalService1PaymentResponseDTO.class);
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

	/**
	 * 
	 * Processes the payment response received from external payment service
	 * provider 1.  
	 * 
	 * @param Response object of payment request to external payment service provider.
	 * @param Response time of the external web service.
	 * @throws JsonProcessingException 
	 * @throws TechnicalException if response received from external service is null or empty.
	 * 
	 */
	private ProcessPaymentResponseDTO processExternalResponse(ResponseEntity<ExternalService1PaymentResponseDTO> responseEntity,
			 long responseTime) throws TechnicalException, JsonProcessingException {

		ProcessPaymentResponseDTO response = new ProcessPaymentResponseDTO();
		ResponseHeader header = new ResponseHeader();
		if (ObjectUtils.isNotEmpty(responseEntity) && ObjectUtils.isNotEmpty(responseEntity.getBody())) {
			 
			ExternalService1PaymentResponseDTO externalResponse = responseEntity.getBody();
			if (LOGGER.isDebugEnabled()) {
				String jsonResponse = objectMapper.writeValueAsString(externalResponse);
				StringBuffer buffer = new StringBuffer();

				buffer.append("\n\t").append("Response received from external payment servie1: ->").append("\n\t")
						.append("Json Response -> ").append(jsonResponse).append("\n\t")
						.append("With Http status code -> ").append(responseEntity.getStatusCode());

				LOGGER.debug(buffer.toString());
			}
			// Depending on the external service success code value might be different
			if (responseEntity.getStatusCode() == HttpStatus.OK && externalResponse.getCode() == 0) {
				header.setSuccessful(true);
			} else {
				LOGGER.error("Payment response received indicates the failure of payment: ");
				header.setDescription(externalResponse.getStatus());
				header.setCode(externalResponse.getCode()+"");
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
