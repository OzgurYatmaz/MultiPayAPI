package com.multipay.routing.dispatchers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multipay.beans.MessageEnums;
import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.integration.external.service2.PaymentService2Client;
import com.multipay.model.Card;
import com.multipay.model.TechnicalException;
import com.multipay.utils.ResponseUtils;

@Component
public class PaymentService2 extends AbstractDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService2.class);

	/**
	 * 
	 * For setting response details
	 * 
	 */
	@Autowired
	private ResponseUtils responseUtils;

	@Autowired
	private PaymentService2Client paymentService2;

	@Override
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest startPaymentRequest, Card card) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("startPayment function started ");
		}

		ProcessPaymentResponse response = new ProcessPaymentResponse();
		try {
			response = paymentService2.processPayment(startPaymentRequest, card);
			responseUtils.setStatusAsSuccess(response, 1);
		} catch (TechnicalException e) {
			StringBuilder builder = new StringBuilder();

			builder.append("\n\t").append("MultiPay Service -> startPayment TecnicalException error").append("\n\t")
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
					MessageEnums.getServiceMessageEnumByWSCode(e.getWsCode()).getMessageCode(), errArgs, 2);
			if (StringUtils.isNotBlank(e.getWsExternalCode())) {
				response.getResponseHeader().setCode(e.getWsExternalCode());
			}
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder();

			builder.append("\n\t").append("MultiPay Service -> startPayment Exception error").append("\n\t")
					.append("Error Details:").append("\n\t").append("Code:")
					.append(MessageEnums.COMMON_SERVICE_ERROR.getWsCode()).append("\n\t").append("Service Message:")
					.append(e.getMessage());

			LOGGER.error(builder.toString());

			responseUtils.setStatusAsFailed(response, MessageEnums.COMMON_SERVICE_ERROR.getMessageCode(),
					new Object[] { e.getMessage() }, 1);
		}

		if (LOGGER.isInfoEnabled()) {
			StringBuilder str = new StringBuilder();

			str.append("\n\t").append("MultiPay Service -> startPayment function completed.").append("\n\t")
					.append("Result: ").append(response.getResponseHeader().isSuccessful());

			LOGGER.info(str.toString());
		}
		return response;
	}

}
