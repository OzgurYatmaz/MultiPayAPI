//package com.multipay.utils;
//
//import java.util.Locale;
//
//import org.springframework.context.MessageSource;
//
//public abstract class ErrorMessageContainer {
//
//	private MessageSource messageSource;
//	private String localeCode = "tr-TR";
//
//	public String getMessage(String messageCode) {
//		return getMessage(messageCode, null);
//	}
//
//	public String getMessage(String messageCode, Object[] args) {
//		return messageSource.getMessage(messageCode, args, Locale.forLanguageTag(localeCode));
//	}
//
//	public void setMessageSource(MessageSource messageSource) {
//		this.messageSource = messageSource;
//	}
//
//	public void setLocaleCode(String localeCode) {
//		this.localeCode = localeCode;
//	}
//
//}