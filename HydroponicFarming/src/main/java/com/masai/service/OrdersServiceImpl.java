package com.masai.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.LogInException;
import com.masai.exception.OrdersException;
import com.masai.exception.PaymentException;
import com.masai.exception.ProduceException;
import com.masai.model.Address;
import com.masai.model.AdminSession;
import com.masai.model.Orders;
import com.masai.model.Payment;
import com.masai.model.Produce;
import com.masai.model.User;
import com.masai.model.UserSession;
import com.masai.repository.AdminSessionDao;
import com.masai.repository.OrdersDao;
import com.masai.repository.PaymentDao;
import com.masai.repository.ProduceDao;
import com.masai.repository.UserDao;
import com.masai.repository.UserSessionDao;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AdminSessionDao adminSessionDao;
	
	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private ProduceDao produceDao;
	
	@Autowired
	private OrdersDao orderDao;

	@Override
	public Orders orderProduce(Integer produceId, Integer paymentId, Integer quantity, Address address, String userkey) throws LogInException, PaymentException, OrdersException, ProduceException {
		
		Optional<UserSession> userSessionOpt = userSessionDao.findByUuId(userkey);
		
		if(userSessionOpt.isEmpty())
			throw new LogInException("User is not logged in");
		
		Optional<Produce> produceOpt = produceDao.findById(produceId);
		
		if(produceOpt.isEmpty())
			throw new ProduceException("Produce not found");
		
		Optional<Payment> paymentOpt = paymentDao.findById(paymentId);
		
		if(paymentOpt.isEmpty())
			throw new PaymentException("Payment method does not exist");
		
		Payment payment = paymentOpt.get();
		
		if(!payment.getActiveStatus()) 
			throw new PaymentException("Payment method is not active right now");
		
		Produce produce = produceOpt.get();
		
		if(produce.getAvailableQuantity() < quantity) 
			throw new ProduceException("Not Enough Quantity Available");
		
		Optional<User> userOpt = userDao.findById(userSessionOpt.get().getUserId());
		
		User user = userOpt.get();
		
		
		Orders order = new Orders();
		
		produce.setAvailableQuantity(produce.getAvailableQuantity() - quantity);
		
		order.setProduceId(produceId);
		order.setAddress(address);
		order.setPaymentId(paymentId);
		order.setTotalPrice(quantity * produce.getSalePrice());
		
		
		Orders savedOrder = orderDao.save(order);
		produceDao.save(produce);
		
		user.getOrderList().add(savedOrder);
		userDao.save(user);
		
		return savedOrder;
	}

	@Override
	public Orders cancelOrder(Integer orderId, String userkey) throws LogInException, OrdersException {
		
		
		Optional<UserSession> userSessionOpt = userSessionDao.findByUuId(userkey);
		
		if(userSessionOpt.isEmpty())
			throw new LogInException("User is not logged in");
		
		Optional<User> userOpt = userDao.findById(userSessionOpt.get().getUserId());
		
		User user = userOpt.get();
		
		List<Orders> orderList = user.getOrderList();
		
		if(orderList.isEmpty())
			throw new OrdersException("Orders not found");
		
		Orders order = null;
		
		for(Orders ord: orderList) {
			if(ord.getOrderId() == orderId) {
				order = ord;
				break;
			}
		}
		
		if(order == null)
			throw new OrdersException("Order not found");
		
		if(order.getIsCancelled())
			throw new OrdersException("Order is Already canncelled");
		
		
		order.setIsCancelled(true);	
		return orderDao.save(order);
	}

	@Override
	public List<Orders> getUsersOrdersList(String userKey) throws LogInException, OrdersException {
		
		Optional<UserSession> userSessionOpt = userSessionDao.findByUuId(userKey);
		
		if(userSessionOpt.isEmpty())
			throw new LogInException("User is not logged in");
		
		Optional<User> userOpt = userDao.findById(userSessionOpt.get().getUserId());
		
		User user = userOpt.get();
		
		List<Orders> orderList = user.getOrderList();
		
		if(orderList.isEmpty())
			throw new OrdersException("Orders not found");
		
		return orderList;
	}

	@Override
	public List<Orders> getAllOrders(String adminKey) throws LogInException, OrdersException {
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		List<Orders> orderList = orderDao.findAll();
		
		if(orderList.isEmpty())
			throw new OrdersException("Orders not found");
		
		return orderList;
	}
	
	
	
	
	
	
	

}
