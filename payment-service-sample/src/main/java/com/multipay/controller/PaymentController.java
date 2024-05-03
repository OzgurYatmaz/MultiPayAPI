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

import com.multipay.error.ErrorDetails;
import com.multipay.model.Payment;
import com.multipay.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment controller", description = "Make  and query payments") // For Swagger
@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	public PaymentService paymentService;

	 

	@Operation(summary = "Fetch by customer or card number", description = "Fethc payments made with parameters card number or customer number")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Payment.class)), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
	    })
	@GetMapping("/fetch-payments")
	public ResponseEntity<List<Payment>> getPaymentsBySearchCriteria(@RequestParam(required = false) String cardNumber,
			@RequestParam(required = false) String customerNumber) throws Exception {
		try {
			List<Payment> payments = paymentService.findPaymentsBySearchCriteria(cardNumber, customerNumber);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
	}

	@Operation(summary = "Fetch by date interval", description = "Fethc payments made with parameters start date and end date")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Payment.class)), mediaType = "application/json") }),
		@ApiResponse(responseCode = "500", description = "When error being occured during querying database", content = { @Content(schema = @Schema(implementation = ErrorDetails.class)) })
	    })
	@GetMapping("/fetch-payments-bydate")
    public ResponseEntity<List<Payment>> getAllPaymentsbyDateInterval(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    					@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws Exception {
		try {
			List<Payment> payments = paymentService.getAllPaymentsbyDateInterval(startDate, endDate);
			return ResponseEntity.ok(payments);

		} catch (Exception e) {
			throw e;
		}
    }
}
