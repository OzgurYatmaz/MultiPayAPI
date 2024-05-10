package com.multipay.utils;

public enum MessageEnums {

	SUCCESSFUL("SUCCESSFUL", 200, "WS-000"),
	FAIL("FAIL", 200, "WS-099"),
	INVALID_PROVIDER_ID("INVALID_PROVIDER_ID", 200, "WS-001"),
	LIMIT_EXCEED("LIMIT_EXCEED", 200, "WS-002"),
	REMOTE_SERVICE_NOT_AVAILABLE("REMOTE_SERVICE_NOT_AVAILABLE", 500, "WS-003"),
	CARD_NOT_EXIST("CARD_NOT_EXIST", 200, "WS-004"),
	COMMON_SERVICE_ERROR("COMMON_SERVICE_ERROR", 500, "WS-005"),
	OPERATION_RESPONSE_NULL("OPERATION_RESPONSE_NULL", 500, "WS-009"),
	ERROR_SENDING_REQUEST("ERROR_SENDING_REQUEST", 500, "WS-006"),
	COMMON_EXTERNAL_SERVICE_ERROR("COMMON_EXTERNAL_SERVICE_ERROR", 500, "WS-018"),
	
	
	
	
	
	NO_RESPONSE_FROM_SERVICE("NO_RESPONSE_FROM_SERVICE", 500, "WS-007"),
	OPERATION_NOT_AVAILABLE("OPERATION_NOT_AVAILABLE", 500, "WS-008"),
	OPERATION_REQUEST_NULL("OPERATION_REQUEST_NULL", 500, "WS-010"),
	UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", 500, "WS-016"),
	AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", 500, "WS-011"),
	INVALID_REQUEST("INVALID_REQUEST", 500, "WS-012"),
	INVALID_CLIENT("INVALID_CLIENT", 500, "WS-013"),
	INVALID_GRANT("INVALID_GRANT", 500, "WS-014"),
	INVALID_SCOPE("INVALID_SCOPE", 500, "WS-015"),
	SERVER_ERROR("SERVER_ERROR", 500, "WS-016"),
	UNEXPECTED_ERROR_OCCURRED("UNEXPECTED_ERROR_OCCURRED", 500, "WS-017");
	
	private String wsCode;
	private int statusCode;
	private String messageCode;

	private MessageEnums(String wsCode, int statusCode, String messageCode) {
		this.wsCode = wsCode;
		this.statusCode = statusCode;
		this.messageCode = messageCode;
	}
	
	public String getWsCode() {
		return wsCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public static MessageEnums getServiceMessageEnumByWSCode(String wsCode) {
		for (MessageEnums e : MessageEnums.values()) {
			if (e.getWsCode().toLowerCase().equalsIgnoreCase(wsCode.toLowerCase())) {
				return e;
			}
		}
		
		return null;
	}
	
	public static MessageEnums getServiceMessageEnumByMessageCode(String messageCode) {
		for (MessageEnums e : MessageEnums.values()) {
			if (e.getMessageCode().toLowerCase().equalsIgnoreCase(messageCode.toLowerCase())) {
				return e;
			}
		}
		return COMMON_SERVICE_ERROR;
	}	
	
}
