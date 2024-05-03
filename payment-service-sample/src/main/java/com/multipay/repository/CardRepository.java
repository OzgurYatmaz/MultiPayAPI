package com.multipay.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.multipay.model.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CardRepository extends JpaRepository<Card, Integer> {

	List<Card> findByCardNumber(String cardNumber);

	Boolean existsByCardNumber(String cardNumber);

	@Modifying
	@Transactional
	@Query("UPDATE Card c SET c.balance = c.balance - :amount WHERE c.id = :cardId")
	int updateBalanceAfterPayment(Integer cardId, double amount);

	
}