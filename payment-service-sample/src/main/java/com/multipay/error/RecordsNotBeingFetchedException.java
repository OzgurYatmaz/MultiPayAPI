package com.multipay.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class RecordsNotBeingFetchedException extends RuntimeException {

	private String errorDetail;

	public RecordsNotBeingFetchedException(String message, String errorDetail) {
		super(message);
		this.errorDetail = errorDetail;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	
	

}
