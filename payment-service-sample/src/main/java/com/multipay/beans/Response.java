package com.multipay.beans;

public class Response {

	protected ResponseHeader responseHeader;
	protected int providerId;
	protected long totalExecutionTime;
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
