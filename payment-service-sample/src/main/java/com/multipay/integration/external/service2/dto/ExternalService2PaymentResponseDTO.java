/**
 * This package is for DTOs of second external payment service provider
 */
package com.multipay.integration.external.service2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * Dummy sample DTO to receive payment response from external service 2
 * 
 * 
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalService2PaymentResponseDTO {

	private String statusCode;
	private String statusMessage;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
