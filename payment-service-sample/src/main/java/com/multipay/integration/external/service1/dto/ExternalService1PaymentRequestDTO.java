/**
 * This package is for DTOs of first external payment service provider
 */
package com.multipay.integration.external.service1.dto;

/**
 * 
 * Dummy sample DTO to send payment request to external service
 * 
 * 
 **/

public class ExternalService1PaymentRequestDTO {

	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
