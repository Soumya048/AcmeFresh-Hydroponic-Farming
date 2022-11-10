package com.masai.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Payment;



@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

	Optional<Payment> findByPaymentName(String paymentName);
	
}
