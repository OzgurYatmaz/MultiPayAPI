package com.multipay.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.multipay.beans.Response;
import com.multipay.beans.ResponseHeader;
import com.multipay.model.StatusMessageConstants;

public class ResponseUtils {


	@Autowired
	private static MessageSource messageSource;
	
	public static void setStatusAsSuccess(Response response, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setSuccessful(true);
		response.getResponseHeader().setDescription(
				messageSource.getMessage(StatusMessageConstants.SUCCESSFUL, null, "Default message", null));
		response.getResponseHeader().setCode(StatusMessageConstants.SUCCESSFUL);
		response.setProviderId(providerId);
	}
	

	public static void setStatusAsFailed(Response response, String error, Object[] args, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setCode(error);
		response.getResponseHeader().setDescription(messageSource.getMessage(error, args, "Default message", null));
		response.getResponseHeader().setSuccessful(false);
		response.setProviderId(providerId);
	}

	private static void createResponseHeader(Response response) {
		if (response.getResponseHeader() == null) {
			ResponseHeader responseHeader = new ResponseHeader();

			response.setResponseHeader(responseHeader);
		}
	}
	
	public static void setResponseTime(Response response, long startTime, long finishTime) {
		createResponseHeader(response);
		response.setTotalExecutionTime((finishTime == 0 ? System.currentTimeMillis() : finishTime) - startTime);
	}
}
