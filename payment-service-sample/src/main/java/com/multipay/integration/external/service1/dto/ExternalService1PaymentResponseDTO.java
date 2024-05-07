package com.multipay.integration.external.service1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * Dummy sample DTO to receive payment response from external service
 * 
 * 
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalService1PaymentResponseDTO {

	private String status;
	private int code;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
