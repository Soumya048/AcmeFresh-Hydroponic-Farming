package com.masai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.LogInException;
import com.masai.exception.PaymentException;
import com.masai.model.AdminSession;
import com.masai.model.Payment;
import com.masai.repository.AdminSessionDao;
import com.masai.repository.PaymentDao;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private AdminSessionDao adminSessionDao;
	
	@Autowired
	private PaymentDao paymentDao;

	@Override
	public Payment addNewPayment(Payment payment, String adminKey) throws PaymentException, LogInException {

		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Payment> paymentOpt = paymentDao.findByPaymentName(payment.getPaymentName());
		
		if(paymentOpt.isPresent())
			throw new PaymentException("Payment method already exist");
		
		return paymentDao.save(payment);
	}

	@Override
	public Payment updatePayment(Payment payment, String adminKey) throws PaymentException, LogInException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Payment> paymentOpt = paymentDao.findById(payment.getPaymentId());
		
		if(!paymentOpt.isPresent())
			throw new PaymentException("Payment method does not exist");
		
		return paymentDao.save(payment);
	}

	@Override
	public Payment deletePayment(Integer paymentId, String adminKey) throws PaymentException, LogInException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Payment> paymentOpt = paymentDao.findById(paymentId);
		
		if(!paymentOpt.isPresent())
			throw new PaymentException("Payment method does not exist");
		
		paymentDao.delete(paymentOpt.get());
		
		return paymentOpt.get();
	}

	@Override
	public Payment togglePaymentActiveSatus(Integer paymentId, String adminKey) throws PaymentException, LogInException {
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Payment> paymentOpt = paymentDao.findById(paymentId);
		
		if(!paymentOpt.isPresent())
			throw new PaymentException("Payment method does not exist");
		
		Payment payment = paymentOpt.get();
		
		payment.setActiveStatus(!payment.getActiveStatus());
		
		return paymentDao.save(payment);
	}

}
