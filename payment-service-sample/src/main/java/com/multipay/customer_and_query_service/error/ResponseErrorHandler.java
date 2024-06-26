/**
 * This package contains classes for handling the exceptions thrown from customer controller.
 * .
 */
package com.multipay.customer_and_query_service.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *  This  class is for handling the exceptions thrown from customer and QueryPayment controllers. I guess method names are self explanatory :)
 * 
 * 
 * @see com.multipay.controller.CustomerController  
 * @see com.multipay.controller.QeryPaymentController  
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-07
 * 
 */

@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 * for managing the validation errors auto controlled by spring validation annotations
	 * 
	 */	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest req) {
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), 
				"Total errors: "+ex.getErrorCount()+" and first error is: "+ex.getFieldError().getDefaultMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, status);
	}

	
	@ExceptionHandler(RecordsNotBeingFetchedException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleRecordsNotBeingFetchedException(RecordsNotBeingFetchedException ex) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(),  ex.getErrorDetail()), status);
	}
	
	@ExceptionHandler(DataInsertionConftlictException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleDataInsertionConftlictExceptionn(DataInsertionConftlictException ex) {

		HttpStatus status = HttpStatus.CONFLICT;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(),  ex.getErrorDetail()), status);
	}
	
	@ExceptionHandler(RecordCouldNotBeSavedException.class) // exception handled
	public ResponseEntity<ErrorDetails> handleRecordCouldNotBeSavedException(RecordCouldNotBeSavedException ex) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;  

		return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), ex.getMessage(), ex.getErrorDetail()), status);
	}
	
	
}
