package com.multipay.model;

public class TechnicalException extends Throwable {

//	private static final long serialVersionUID = 1470210843823469582L;

	public TechnicalException() {
		super();
	}

	private String wsMessage;
	private String wsCode;
	/**
	 * Dis sistemden gelen kodun basina ws konarak olusturulmasi ile olusan kod.
	 */
	private String wsExternalCode;

	public TechnicalException(String wsCode, String wsMessage) {
		this.wsCode = wsCode;
		this.wsMessage = wsMessage;
	}

	public TechnicalException(String wsCode, String wsExternalCode, String wsMessage) {
		this.wsCode = wsCode;
		this.wsExternalCode = wsExternalCode;
		this.wsMessage = wsMessage;
	}

	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getWsMessage() {
		return wsMessage;
	}

	public void setWsMessage(String wsMessage) {
		this.wsMessage = wsMessage;
	}

	public String getWsCode() {
		return wsCode;
	}

	public void setWsCode(String wsCode) {
		this.wsCode = wsCode;
	}

	public String getWsExternalCode() {
		return wsExternalCode;
	}

	public void setWsExternalCode(String wsExternalCode) {
		this.wsExternalCode = wsExternalCode;
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable cause) {
		super(cause);
	}
}