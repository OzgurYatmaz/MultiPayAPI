package com.multipay.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.multipay.beans.MessageEnums;
import com.multipay.beans.Response;
import com.multipay.beans.ResponseHeader;

@Component
public class ResponseUtils {


	@Autowired
	private MessageSource messageSource;
	
	public  void setStatusAsSuccess(Response response, int providerId) {
		createResponseHeader(response);

		response.getResponseHeader().setSuccessful(true);
		response.getResponseHeader().setDescription(messageSource.getMessage(MessageEnums.SUCCESSFUL.getMessageCode(), null, null, null));
		response.getResponseHeader().setCode(messageSource.getMessage(MessageEnums.SUCCESSFUL.getWsCode(), null, null, null));
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
