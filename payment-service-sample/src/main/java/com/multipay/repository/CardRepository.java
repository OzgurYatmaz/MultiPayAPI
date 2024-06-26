/**
 * This package for interfaces responsible for database operations
 */
package com.multipay.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.multipay.entity.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * Card related operations in database are done here
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */
public interface CardRepository extends JpaRepository<Card, Integer> {

	/**
	 * 
	 * Fetches all cards with given card number from data base.
	 * 
	 * @param cardNumber
	 * 
	 */
	public List<Card> findByCardNumber(String cardNumber);

	/**
	 * 
	 * Checks if the provided card number corresponds any card in database.
	 * 
	 * @param cardNumber
	 * 
	 */
	public Boolean existsByCardNumber(String cardNumber);

	/**
	 * 
	 * After payment made it updates balance of the card in database
	 * 
	 * @param cardId: id of the card from which payment is made.
	 * @param amount: amount of payment made from the balance of the card..
	 * 
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Card c SET c.balance = c.balance - :amount WHERE c.id = :cardId")
	public int updateBalanceAfterPayment(Integer cardId, double amount);

}