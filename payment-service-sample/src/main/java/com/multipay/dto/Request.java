package com.multipay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class Request {

	@Schema(description = "Id of external payment service provider", example = "1")
	protected int providerId;

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	 
}
