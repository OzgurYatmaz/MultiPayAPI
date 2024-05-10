package com.multipay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Common Response object for all responses of this api")
public class ResponseHeader {

	@Schema(description = "Id of external payment service provider", example = "SUCCESSFUL")
	protected String code;
	@Schema(description = "Description of code", example = "Operation is successful")
	protected String description;
	@Schema(description = "If operation is successful", example = "true")
	protected boolean successful;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
