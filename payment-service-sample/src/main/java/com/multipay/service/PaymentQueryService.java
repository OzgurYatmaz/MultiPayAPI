/**
 * This package contains classes for services - business logics
 */
package com.multipay.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.multipay.customer_and_query_service.error.ParametersNotProvidedException;
import com.multipay.customer_and_query_service.error.RecordsNotBeingFetchedException;
import com.multipay.dto.PaymentDTO;
import com.multipay.entity.Payment;
import com.multipay.repository.PaymentRepository;

/**
 * Main payment query service's business logic is executed here
 * 
 * @throws Various exceptions explaining the reasons of failures.
 * 
 * @see com.multipay.customer_and_query_service.error.ResponseErrorHandler class
 *      to see possible errors might be thrown from here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-09
 * 
 */
@Service
public class PaymentQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentQueryService.class);

	/**
	 * Payment records related database operations are done with this
	 */
	@Autowired
	private PaymentRepository paymentRepository;

	/**
	 * 
	 * Fetches payment records with cutomerNumber or cardNumber or both. Both of the
	 * parameters are optional
	 * 
	 * @param cutomerNumber if it is null it will be disregarded in repository.
	 * @param cardNumber    if it is null it will be disregarded in repository.
	 * 
	 * @throws RecordsNotBeingFetchedException if there is a failure in reaching
	 *                                         database records.
	 * @throws ParametersNotProvidedException  if both of the parameters are not
	 *                                         supplied
	 * 
	 */
	@SuppressWarnings("deprecation")
	public List<PaymentDTO> findPaymentsBySearchCriteria(String cardNumber, String customerNumber) throws Exception {

		if (StringUtils.isEmpty(customerNumber) && StringUtils.isEmpty(cardNumber)) {
			throw new ParametersNotProvidedException("Both Arguments cannot be empty",
					"Please provide cardNumber or customerNumber or both");
		}

		List<Payment> paymentsFetched;
		try {
			paymentsFetched = paymentRepository.findByCardNumberOrCustomerNumber(customerNumber, cardNumber);
			List<PaymentDTO> payments = convertPaymentEntityToDTO(paymentsFetched);
			return payments;
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching payment records from DB: ", e);
			throw new RecordsNotBeingFetchedException("Error occured while fetching payment records from data base",
					e.getMessage());
		}
	}

	/**
	 * 
	 * Fetches payment records for time interval between startDate and endDate.
	 * 
	 * @param startDate, compulsory and definition is self explanatory :) format:
	 *                   YYYY-MM-DD example: 2024-04-27
	 * @param endDate,   compulsory and definition is self explanatory :) format:
	 *                   YYYY-MM-DD example: 2024-04-27
	 * 
	 * @throws RecordsNotBeingFetchedException is thrown if there is a failure in
	 *                                         reaching database records.
	 * 
	 */
	public List<PaymentDTO> getAllPaymentsbyDateInterval(LocalDate startDate, LocalDate endDate) throws Exception {
		List<Payment> paymentsFetched;
		try {
			paymentsFetched = paymentRepository.getAllPaymentsBetweenDates(convertToLocalDateTime(startDate),
					convertToLocalDateTime(endDate));
			List<PaymentDTO> payments = convertPaymentEntityToDTO(paymentsFetched);
			return payments;
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching payment records from data base: ", e);
			throw new RecordsNotBeingFetchedException("Error occured while fetching payment records from data base",
					e.getMessage());
		}
	}

	/**
	 * 
	 * Entity object fetched from database is converted to DTO object for web
	 * service return.
	 * 
	 * @param List of entity objects fetched from database.
	 * 
	 * @return List of DTO objects for API return type.
	 * 
	 * 
	 */
	private List<PaymentDTO> convertPaymentEntityToDTO(List<Payment> paymentsFetched) {
		if (!CollectionUtils.isEmpty(paymentsFetched)) {
			List<PaymentDTO> paymentsForResponse = new ArrayList<PaymentDTO>();
			for (Payment p : paymentsFetched) {
				PaymentDTO tempPayment = new PaymentDTO();
				tempPayment.setAmount(p.getAmount());
				tempPayment.setCustomerNumber(p.getCustomerNumber());
				tempPayment.setCardNumber(p.getCardNumber());
				tempPayment.setPaymentDate(p.getPaymentDate());
				paymentsForResponse.add(tempPayment);
			}

			return paymentsForResponse;

		}
		return null;
	}

	/**
	 * 
	 * Converts date object to local date time for further database querying.
	 * 
	 * @param dateObject of format: YYYY-MM-DD example: 2024-04-27
	 * 
	 * 
	 */
	private LocalDateTime convertToLocalDateTime(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MIDNIGHT);
	}
}
