package com.multipay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.multipay.controller.MultiPayRestService;
import com.multipay.dto.AddCustomerRequestDTO;
import com.multipay.dto.CardDTO;
import com.multipay.dto.ProcessPaymentRequestDTO;
import com.multipay.entity.Customer;
import com.multipay.entity.Payment;
import com.multipay.repository.CustomerRepository;
import com.multipay.repository.PaymentRepository;
import com.multipay.service.CustomerService;

/**
 * 
 * This class is for unit tests. All unit test are executed here
 * maven-surefire-report-plugin is used to generate test reports.
 * Test report is generated here: /target/site/surefire-report.html
 * 
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-06
 * 
 */

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)//needed for clening db records function
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceSampleApplicationTests {



	/**
	 * 
	 * Payment related database operations will be done with this
	 * 
	 */
	@Autowired
	private PaymentRepository paymentRepository;
	


	/**
	 * 
	 * Customer related operations will be done with this
	 * 
	 */
	@Autowired
	private CustomerRepository customerRepository;
	

	/**
	 * 
	 * Customer related operations will be done with this
	 * 
	 */
	@Autowired
	private CustomerService customerService;
	

	/**
	 * 
	 * Business logic of payment operation
	 * 
	 */
	@Autowired
	private MultiPayRestService paymentService;


	/**
	 * 
	 * Clears all records in database to make it ready for tests
	 * 
	 */
    @BeforeAll
    @Transactional
	void prepereingDBForTests() {
		customerRepository.deleteAll();
		paymentRepository.deleteAll();
	}
    
    
    /**
	 * 
	 * Adds first customer with two cards to database.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Add customer")
	@Order(1)
	void addCustomer1() {
		AddCustomerRequestDTO c = new AddCustomerRequestDTO();
		List<CardDTO> cards = new ArrayList<CardDTO>();
		CardDTO c1 = new CardDTO();
		c1.setCardNumber("571-1");
		c1.setBalance(1000);
		CardDTO c2 = new CardDTO();
		c2.setCardNumber("571-2");
		c2.setBalance(500);
		cards.add(c1);
		cards.add(c2);
		
		c.setCustomerNumber("114-1");
		c.setEmail("ozguryatmaz@yandex.com");
		c.setName("ozgur yatmaz");
		c.setCards(cards);
		
		Customer customerSaved = null;
		try {
			 customerSaved = customerService.addCustomer(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(customerSaved);
        assertEquals(c.getName(), customerSaved.getName());
        assertEquals(c.getEmail(), customerSaved.getEmail());
        assertEquals(2, customerSaved.getCards().size());
        assertEquals("571-1", customerSaved.getCards().get(0).getCardNumber());
	}
	
	/**
	 * 
	 * Adds second customer with one card to database.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Add customer")
	@Order(2)
	void addCustomer2() {
		AddCustomerRequestDTO c = new AddCustomerRequestDTO();
		List<CardDTO> cards = new ArrayList<CardDTO>();
		CardDTO c1 = new CardDTO();
		c1.setCardNumber("571-3");
		c1.setBalance(750);
		cards.add(c1);
		
		c.setCustomerNumber("114-2");
		c.setEmail("ozguryatmaz@gmail.com");
		c.setName("ozgur2 yatmaz2");
		c.setCards(cards);
		
		Customer customerSaved = null;
		try {
			 customerSaved = customerService.addCustomer(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertEquals(1, customerSaved.getCards().size());
        assertEquals("571-3", customerSaved.getCards().get(0).getCardNumber());
	}
	
	/**
	 * 
	 * Checks if 2 customers added to database with previous operation are accessible.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Fetch all customers")
	@Order(3)
	void fetchAllCustomersTest() {
		List<Customer> customers = customerRepository.findAll();
		assertNotNull(customers);
		assertEquals(2, customers.size());
        assertEquals("114-1", customers.get(0).getCustomerNumber());
	}
	
	/**
	 * 
	 * Makes 3 payments from 3 cards added with previous operations.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Make 3 payments")
	@Order(4)
	void makePaymentTest() {
		ProcessPaymentRequestDTO r1 = new ProcessPaymentRequestDTO();
		ProcessPaymentRequestDTO r2 = new ProcessPaymentRequestDTO();
		ProcessPaymentRequestDTO r3 = new ProcessPaymentRequestDTO();
		r1.setCardNumber("571-1");
		r2.setCardNumber("571-2");
		r3.setCardNumber("571-3");
		
		r1.setProviderId(1);
		r2.setProviderId(1);
		r3.setProviderId(1);
		
		r1.setAmount(100.00);
		r2.setAmount(101.50);
		r3.setAmount(203.45);
		
		try {
			paymentService.startPayment(r1);
			paymentService.startPayment(r2);
			paymentService.startPayment(r3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Checks if payments are fetched by customer number.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Fetch by customerNumber")
	@Order(5)
	void findPaymentsByCustomerNumber() {
		List<Payment> payments = paymentRepository.findByCardNumberOrCustomerNumber("114-1", null);

		assertNotNull(payments);
        assertEquals(2, payments.size());
        assertEquals("571-1", payments.get(0).getCardNumber());
     
	}
	
	/**
	 * 
	 * Checks if payments are fetched by card number.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Fetch by  CardNumber")
	@Order(6)
	void findPaymentsByCardNumber() {
		List<Payment> payments = paymentRepository.findByCardNumberOrCustomerNumber(null, "571-1");

		assertNotNull(payments);
        assertEquals(1, payments.size());
        assertEquals("571-1", payments.get(0).getCardNumber());
    
	 
	}
	/**
	 * 
	 * Checks if payments are fetched by both customer number and card number.
	 * 
	 */
	@Test
//	@Disabled
	@DisplayName("Fetch by customerNumber and CardNumber")
	@Order(7)
	void findPaymentsByCustomerNumberAndCardNumber() {
		List<Payment> payments = paymentRepository.findByCardNumberOrCustomerNumber("114-1", "571-1");

		assertNotNull(payments);
        assertEquals(1, payments.size());
        assertEquals("571-1", payments.get(0).getCardNumber());
    
	 
	}

}
