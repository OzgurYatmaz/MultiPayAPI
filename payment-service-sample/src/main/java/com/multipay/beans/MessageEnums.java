package com.multipay.beans;

public enum MessageEnums {

	SUCCESSFUL("SUCCESSFUL", 200, "SERVICE-000"),
	COMMON_SERVICE_ERROR("COMMON_SERVICE_ERROR", 500, "SERVICE-001"),
	INVALID_CARD_NUMBER("CARD_NOT_EXIST", 400, "SERVICE-033"),
	ERROR_SENDING_REQUEST("INVALID_REQUEST", 500, "SERVICE-002"),
	NO_RESPONSE_FROM_SERVICE("NO_RESPONSE_FROM_SERVICE", 500, "SERVICE-003"),
	OPERATION_NOT_AVAILABLE("OPERATION_NOT_AVAILABLE", 500, "SERVICE-005"),
	
	OPERATION_RESPONSE_NULL("OPERATION_RESPONSE_NULL", 500, "SERVICE-014"),
	OPERATION_REQUEST_NULL("OPERATION_REQUEST_NULL", 500, "SERVICE-015"),
	UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", 500, "SERVICE-016"),
	REMOTE_SERVICE_NOT_AVAILABLE("REMOTE_SERVICE_NOT_AVAILABLE", 500, "SERVICE-017"),
	
	
	AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", 500, "SERVICE-WS-001"),
	INVALID_REQUEST("INVALID_REQUEST", 500, "SERVICE-WS-002"),
	INVALID_CLIENT("INVALID_CLIENT", 500, "SERVICE-WS-003"),
	INVALID_GRANT("INVALID_GRANT", 500, "SERVICE-WS-004"),
	INVALID_SCOPE("INVALID_SCOPE", 500, "SERVICE-WS-005"),
	SERVER_ERROR("SERVER_ERROR", 500, "SERVICE-WS-014"),
	LIMIT_EXCEED("LIMIT_EXCEED", 500, "SERVICE-WS-033"),
	UNEXPECTED_ERROR_OCCURRED("UNEXPECTED_ERROR_OCCURRED", 500, "SERVICE-WS-036"),
	COMMON_EXTERNAL_SERVICE_ERROR("COMMON_EXTERNAL_SERVICE_ERROR", 500, "SERVICE-WS-037");
	
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

	public static MessageEnums getServiceMessageEnumByWSCode(String wsCode, boolean returnDefaultValue, boolean isExternal) {
		for (MessageEnums e : MessageEnums.values()) {
			if (e.getWsCode().toLowerCase().equalsIgnoreCase(wsCode.toLowerCase())) {
				return e;
			}
		}
		// null or empty situation
		if (returnDefaultValue) {
			if (isExternal) {
				return COMMON_EXTERNAL_SERVICE_ERROR;
			}
			return COMMON_SERVICE_ERROR;
		}
		return null;
	}
	
	public static MessageEnums getServiceMessageEnumByMessageCode(String messageCode, boolean isExternal) {
		for (MessageEnums e : MessageEnums.values()) {
			if (e.getMessageCode().toLowerCase().equalsIgnoreCase(messageCode.toLowerCase())) {
				return e;
			}
		}
		// null or empty situation
		if (isExternal) {
			return COMMON_EXTERNAL_SERVICE_ERROR;
		}
		return COMMON_SERVICE_ERROR;
	}	
	
}
