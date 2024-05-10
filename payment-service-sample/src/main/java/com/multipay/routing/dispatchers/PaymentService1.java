/**
 * This package is for all main services of the integrated payment service providers
 */
package com.multipay.routing.dispatchers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.dto.ProcessPaymentResponseDTO;
import com.multipay.entity.TechnicalException;
import com.multipay.integration.external.service1.PaymentService1Client;
import com.multipay.utils.MessageEnums;
import com.multipay.utils.ResponseUtils;

/**
 * 
 * This is for service layer for integration to external payment service provider 1
 * 
 */
@Component
public class PaymentService1 extends AbstractDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService1.class);

	/**
	 * 
	 * For setting response details
	 * 
	 */
	@Autowired
	private ResponseUtils responseUtils;

	/**
	 * 
	 * For setting communicating with external payment service provider1
	 * 
	 */
	@Autowired
	private PaymentService1Client externalServiceClient;

	/**
	 * 
	 * Makes payment from external payment service provider 1 by using client class
	 * 
	 * @param paymentRequest object 
	 * @return Payment response object includes info if payment is successful or
	 *         failed.
	 * 
	 */
	@Override
	public ProcessPaymentResponseDTO startPayment(ProcessPaymentRequestDTO startPaymentRequest) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("startPayment function started ");
		}

		ProcessPaymentResponseDTO response = new ProcessPaymentResponseDTO();
		try {
			response = externalServiceClient.processPayment(startPaymentRequest);
			
		} catch (TechnicalException e) {
			StringBuilder builder = new StringBuilder();

			builder.append("\n\t").append("MultiPay Service 1 -> startPayment TecnicalException error").append("\n\t")
					.append("Error Details:").append("\n\t").append("Code:").append(e.getWsCode()).append("\n\t")
					.append("WS Message:").append(e.getWsMessage()).append("\n\t").append("Service Message:")
					.append(e.getMessage());

			LOGGER.error(builder.toString());
//			LOGGER.error(e, e);

			Object[] errArgs = null;

			if (StringUtils.isNotBlank(e.getWsExternalCode())) {
				errArgs = new Object[] { e.getWsExternalCode(), e.getWsMessage() };
			} else {
				errArgs = new Object[] { e.getWsMessage() };
			}
			responseUtils.setStatusAsFailed(response,
					MessageEnums.getServiceMessageEnumByWSCode(e.getWsCode()).getMessageCode(), errArgs, 1);
			if (StringUtils.isNotBlank(e.getWsExternalCode())) {
				response.getResponseHeader().setCode(e.getWsExternalCode());
			}
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder();

			builder.append("\n\t").append("MultiPay Service 1 -> startPayment Exception error").append("\n\t")
					.append("Error Details:").append("\n\t").append("Code:")
					.append(MessageEnums.COMMON_SERVICE_ERROR.getWsCode()).append("\n\t").append("Service Message:")
					.append(e.getMessage());

			LOGGER.error(builder.toString());

			responseUtils.setStatusAsFailed(response, MessageEnums.COMMON_SERVICE_ERROR.getMessageCode(),
					new Object[] { e.getMessage() }, 1);
		}

		if (LOGGER.isInfoEnabled()) {
			StringBuilder str = new StringBuilder();

			str.append("\n\t").append("MultiPay Service 1 -> startPayment function completed.").append("\n\t")
					.append("Result: ").append(response.getResponseHeader().isSuccessful());

			LOGGER.info(str.toString());
		}
		return response;
	}


}
