/**
 * This package contains classes for project configurations.
 */
package com.multipay.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class reads values from application.properties file.
 * 
 * @see /src/main/resources/application-dev.properties
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */

@ConfigurationProperties(prefix = "payment-service")
@Component
public class PaymentServiceParameters {

	/**
	 * Uri of the first external payment service provider
	 */
	private String paymentserviceurl;

	/**
	 * Uri of the second external payment service provider
	 */
	private String paymentservice2url;

	/**
	 * For connection timeout time in milliseconds when sending request to external payment service providers
	 */
	private int timeout;

	public String getPaymentserviceurl() {
		return paymentserviceurl;
	}

	public void setPaymentserviceurl(String paymentserviceurl) {
		this.paymentserviceurl = paymentserviceurl;
	}

	public String getPaymentservice2url() {
		return paymentservice2url;
	}

	public void setPaymentservice2url(String paymentservice2url) {
		this.paymentservice2url = paymentservice2url;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
