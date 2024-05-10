/**
 * This package contains classes for all controllers of the project
 * .
 */
package com.multipay.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multipay.customer_and_query_service.error.ErrorDetails;
import com.multipay.dto.PaymentDTO;
import com.multipay.service.PaymentQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Main payment query controller to fetch payments from database
 * 
 * @throws Various exceptions explaining the reasons of failures.
 * 
 * @see com.multipay.customer_and_query_service.error.ResponseErrorHandler class
 *      to see possible errors might be thrown from here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */
@Tag(name = "Query Payment controller", description = "Query payments from database") // For Swagger
@RestController
@RequestMapping("multipay/payments")
public class QueryPaymentController {

	/**
	 * 
	 * Payment related operations will be done with this
	 * 
	 */
	@Autowired
	public PaymentQueryService paymentService;

	/**
	 * 
	 * To query payments from database with two optional parameters
	 * 
	 * @param customerNumber
	 * @param cardNumber.
	 * @return list of Payment objects
	 * @throws RecordsNotBeingFetchedException exception with message explaining the
	 *                                         error detail.
	 * 
	 * 
	 */
	@Operation(summary = "Fetch by customer or card number", description = "Fethc payments made with parameters card number or customer number")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDTO.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping("/fetch-payments")
	public ResponseEntity<List<PaymentDTO>> getPaymentsBySearchCriteria(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String customerNumber) throws Exception {
		try {
			List<PaymentDTO> payments = paymentService.findPaymentsBySearchCriteria(cardNumber, customerNumber);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * To query payments from database with two compulsory parameters defining the
	 * date interval
	 * 
	 * @param sartDate format: YYYY-MM-DD example: 2024-04-27
	 * @param endDate  format: YYYY-MM-DD example: 2024-04-28
	 * @return list of Payment objects
	 * @throws RecordsNotBeingFetchedException exception with message explaining the
	 *                                         error detail if payment records
	 *                                         cannot be fetched.
	 * @throws Validation error if one of the parameters are not supplied.
	 * 
	 * 
	 */
	@Operation(summary = "Fetch by date interval", description = "Fethc payments made with parameters start date and end date")
	@ApiResponses({ @ApiResponse(responseCode = "200", content = {
			@Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDTO.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = {
					@Content(schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping("/fetch-payments-bydate")
	public ResponseEntity<List<PaymentDTO>> getAllPaymentsbyDateInterval(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
			throws Exception {
		try {
			List<PaymentDTO> payments = paymentService.getAllPaymentsbyDateInterval(startDate, endDate);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
	}
}
