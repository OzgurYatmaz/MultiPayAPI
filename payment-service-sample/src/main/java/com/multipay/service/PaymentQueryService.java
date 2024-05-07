package com.multipay.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multipay.customer_and_query_service.error.RecordsNotBeingFetchedException;
import com.multipay.model.Payment;
import com.multipay.repository.PaymentRepository;

@Service
public class PaymentQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentQueryService.class);

	@Autowired
	private PaymentRepository paymentRepository;


	public List<Payment> findPaymentsBySearchCriteria(String cardNumber, String customerNumber) throws Exception {

		List<Payment> payments;
		try {
			payments = paymentRepository.findByCardNumberOrCustomerNumber(customerNumber, cardNumber);
			return payments;
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching payment records from DB: ", e);
			throw new RecordsNotBeingFetchedException("Error occured while fetching payment records from data base",
					e.getMessage());
		}
	}

	public List<Payment> getAllPaymentsbyDateInterval(LocalDate startDate, LocalDate endDate) throws Exception {
		List<Payment> payments;
		try {
			payments = paymentRepository.getAllPaymentsBetweenDates(convertToLocalDateTime(startDate), convertToLocalDateTime(endDate));
			return payments;
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching payment records from data base: ", e);
			throw new RecordsNotBeingFetchedException("Error occured while fetching payment records from data base",
					e.getMessage());
		}
	}
	
	private LocalDateTime convertToLocalDateTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }
}
