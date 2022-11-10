package com.masai.service;

import com.masai.exception.LogInException;
import com.masai.exception.PaymentException;
import com.masai.model.Payment;

public interface PaymentService {
	
	public Payment addNewPayment(Payment payment, String adminKey) throws PaymentException, LogInException;
	public Payment updatePayment(Payment payment, String adminKey) throws PaymentException, LogInException;;
 	public Payment deletePayment(Integer paymentId, String adminKey) throws PaymentException, LogInException;
 	public Payment togglePaymentActiveSatus(Integer paymentId, String adminKey) throws PaymentException, LogInException;
}
