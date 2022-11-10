package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.dto.LoginDTO;
import com.masai.exception.LogInException;
import com.masai.exception.OrdersException;
import com.masai.exception.PaymentException;
import com.masai.exception.ProduceException;
import com.masai.exception.UserException;
import com.masai.model.Address;
import com.masai.model.Orders;
import com.masai.model.Produce;
import com.masai.model.User;
import com.masai.model.UserSession;
import com.masai.service.OrdersService;
import com.masai.service.ProduceService;
import com.masai.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProduceService produceService;
	
	@Autowired
	private OrdersService ordersServcie;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserException {
		User newUser = userService.registerUser(user);
		return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserSession> loginUserHandler(@RequestBody LoginDTO loginDto) throws LogInException, UserException {
		UserSession currentSession = userService.loginUser(loginDto);
		return new ResponseEntity<UserSession>(currentSession, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<String> logoutUserHandler(@RequestParam String key) throws UserException {
		String message = userService.logoutUser(key);
		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUserHandler(@Valid @RequestBody User user, @RequestParam String key) throws LogInException, UserException {
		User updateUser = userService.updateUser(user, key);
		return new ResponseEntity<User>(updateUser, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<User> deleteUserByIdHandler(@RequestParam String key) throws UserException, LogInException{
		User deletedUser = userService.deleteUser(key);
		return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
	}
	
	@GetMapping("/produce")
	public ResponseEntity<List<Produce>> findAllProduceHandler(@RequestParam String key) throws LogInException, ProduceException  {
		List<Produce> produceList = produceService.getAllProduce(key);
		return new ResponseEntity<List<Produce>>(produceList, HttpStatus.OK);
	}
	
	@PostMapping("/order/{produceId}/{paymentId}/{quantity}")
	public ResponseEntity<Orders> orderProduceHandler(@RequestBody Address address, @PathVariable Integer produceId, @PathVariable Integer paymentId, @PathVariable Integer quantity, @RequestParam String key) throws LogInException, PaymentException, OrdersException, ProduceException  {
		Orders OrderedProduce = ordersServcie.orderProduce(produceId, paymentId, quantity, address, key);
		return new ResponseEntity<Orders>(OrderedProduce, HttpStatus.OK);
	}
	
	@GetMapping("/user/orders")
	public ResponseEntity<List<Orders>> findAllUserOrdersHandler(@RequestParam String key) throws LogInException, OrdersException  {
		List<Orders> ordersList = ordersServcie.getUsersOrdersList(key);
		return new ResponseEntity<List<Orders>>(ordersList, HttpStatus.OK);
	}
	
	@PatchMapping("/cancel/order/{orderId}")
	public ResponseEntity<Orders> orderProduceHandler(@PathVariable Integer orderId, @RequestParam String key) throws LogInException, OrdersException   {
		Orders canceledOrder = ordersServcie.cancelOrder(orderId, key);
		return new ResponseEntity<Orders>(canceledOrder, HttpStatus.OK);
	}
	
}
