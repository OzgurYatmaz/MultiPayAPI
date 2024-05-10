/**
 * This package is for DTOs of second external payment service provider
 */
package com.multipay.integration.external.service2.dto;

import java.util.Date;

/**
 * Dummy sample DTO to send payment request to external service 2
 * 
 */

public class ExternalService2PaymentRequestDTO {

	private double amount;
	private Date paymentDate;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
