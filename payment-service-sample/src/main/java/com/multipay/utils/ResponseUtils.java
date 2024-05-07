package com.multipay.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.multipay.beans.Response;
import com.multipay.beans.ResponseHeader;
import com.multipay.model.StatusMessageConstants;

@Component
public class ResponseUtils {


	@Autowired
	private MessageSource messageSource;
	
	public  void setStatusAsSuccess(Response response, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setSuccessful(true);
		response.getResponseHeader().setDescription(
				messageSource.getMessage(StatusMessageConstants.SUCCESSFUL, null, "Default message", null));
		response.getResponseHeader().setCode(StatusMessageConstants.SUCCESSFUL);
		response.setProviderId(providerId);
	}
	

	public void setStatusAsFailed(Response response, String error, Object[] args, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setCode(error);
		response.getResponseHeader().setDescription(messageSource.getMessage(error, args, null, null));
		response.getResponseHeader().setSuccessful(false);
		response.setProviderId(providerId);
	}

	private void createResponseHeader(Response response) {
		if (response.getResponseHeader() == null) {
			ResponseHeader responseHeader = new ResponseHeader();

			response.setResponseHeader(responseHeader);
		}
	}
	
	public  void setResponseTime(Response response, long startTime, long finishTime) {
		createResponseHeader(response);
		response.setTotalExecutionTime((finishTime == 0 ? System.currentTimeMillis() : finishTime) - startTime);
	}
}
