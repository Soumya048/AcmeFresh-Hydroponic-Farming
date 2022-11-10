package com.masai.service;

import java.util.List;

import com.masai.exception.LogInException;
import com.masai.exception.OrdersException;
import com.masai.exception.PaymentException;
import com.masai.exception.ProduceException;
import com.masai.model.Address;
import com.masai.model.Orders;

public interface OrdersService {
	
	
	public Orders orderProduce(Integer produceId, Integer paymentId, Integer quantity, Address address, String userkey) throws LogInException, PaymentException, OrdersException, ProduceException;
	public Orders cancelOrder(Integer orderId, String userkey) throws LogInException, OrdersException;
	public List<Orders> getUsersOrdersList(String userKey) throws LogInException, OrdersException;
	public List<Orders> getAllOrders(String adminKey) throws LogInException, OrdersException;
	
	
	
}
