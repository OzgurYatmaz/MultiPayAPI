package com.multipay.routing.dispatchers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multipay.beans.MessageEnums;
import com.multipay.beans.ProcessPaymentRequest;
import com.multipay.beans.ProcessPaymentResponse;
import com.multipay.beans.Response;
import com.multipay.integration.external.service1.PaymentService1Client;
import com.multipay.model.Card;
import com.multipay.model.TechnicalException;

@Component
public class PaymentService1 extends AbstractDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService1.class);

	@Autowired
	private PaymentService1Client paymentService1;

	@Override
	public ProcessPaymentResponse startPayment(ProcessPaymentRequest startPaymentRequest, Card card) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("startPayment function started ");
		}
		long startTime = System.currentTimeMillis(), finishTime = 0;

		ProcessPaymentResponse response = new ProcessPaymentResponse();
		try {
			response = paymentService1.processPayment(startPaymentRequest, card);
			setStatusAsSuccess(response, 1);
		} catch (TechnicalException e) {
			StringBuilder builder = new StringBuilder();

			builder.append("\n\t").append("MultiPay Service -> startPayment TecnicalException error").append("\n\t")
					.append("Error Details:").append("\n\t").append("Code:").append(e.getWsCode()).append("\n\t")
					.append("WS Message:").append(e.getWsMessage()).append("\n\t").append("Service Message:")
					.append(e.getMessage());

			LOGGER.error(builder.toString());
//			LOGGER.error(e, e);

			finishTime = System.currentTimeMillis();

			Object[] errArgs = null;

			if (StringUtils.isNotBlank(e.getWsExternalCode())) {
				errArgs = new Object[] { e.getWsExternalCode(), e.getWsMessage() };
			} else {
				errArgs = new Object[] { e.getWsMessage() };
			}
			System.out.println("-----------------------------------------------");
			System.out.println(MessageEnums.getServiceMessageEnumByWSCode(e.getWsCode(), true, false));
			System.out.println(MessageEnums.getServiceMessageEnumByWSCode(e.getWsCode(), true, false).getMessageCode());
			System.out.println("-----------------------------------------------");
			setStatusAsFailed(response,
					MessageEnums.getServiceMessageEnumByWSCode(e.getWsCode(), true, false).getMessageCode(), errArgs,
					1);
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
//			LOGGER.error(e, e);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(MessageEnums.COMMON_SERVICE_ERROR.getMessageCode());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

			finishTime = System.currentTimeMillis();

			setStatusAsFailed(response, MessageEnums.COMMON_SERVICE_ERROR.getMessageCode(),
					new Object[] { e.getMessage() }, 1);
		} finally {
			finishTime = System.currentTimeMillis();
			setResponseTime(response, startTime, finishTime);
		}

		if (LOGGER.isInfoEnabled()) {
			StringBuilder str = new StringBuilder();

			str.append("\n\t").append("MultiPay Service -> startPayment function completed.").append("\n\t")
					.append("Result: ").append(response.getResponseHeader().isSuccessful()).append("\n\t")
					.append("Response Execution Time: ").append(response.getExternalExecutionTime()).append("\n\t")
					.append("Total Execution Time: ").append(response.getTotalExecutionTime()).append("\n");

			LOGGER.info(str.toString());
		}
		return response;
	}

}
