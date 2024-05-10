package com.multipay.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class Response {

	
	protected ResponseHeader responseHeader;
	@Schema(description = "Id of external payment service provider", example = "1")
	protected int providerId;
	@Schema(description = "Response time of this API in milliseconds", example = "115")
	protected long totalExecutionTime;
	@Schema(description = "Response time of exteranal payment service provider in milliseconds", example = "85")
	protected long externalExecutionTime;

	
	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader value) {
		this.responseHeader = value;
	}

	public long getTotalExecutionTime() {
		return totalExecutionTime;
	}

	public void setTotalExecutionTime(long totalExecutionTime) {
		this.totalExecutionTime = totalExecutionTime;
	}

	public long getExternalExecutionTime() {
		return externalExecutionTime;
	}

	public void setExternalExecutionTime(long externalExecutionTime) {
		this.externalExecutionTime = externalExecutionTime;
	}

}
