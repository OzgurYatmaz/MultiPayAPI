package com.multipay.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "payment-service")
@Component
public class PaymentServiceConfig {

	private String paymentserviceurl;
	private String paymentservice2url;

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
