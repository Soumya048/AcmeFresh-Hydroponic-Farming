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
import com.masai.exception.AdminException;
import com.masai.exception.CategoryException;
import com.masai.exception.LogInException;
import com.masai.exception.OrdersException;
import com.masai.exception.PaymentException;
import com.masai.exception.ProduceException;
import com.masai.exception.UrlException;
import com.masai.model.Admin;
import com.masai.model.AdminSession;
import com.masai.model.Category;
import com.masai.model.Orders;
import com.masai.model.Payment;
import com.masai.model.Produce;
import com.masai.repository.OrdersDao;
import com.masai.service.AdminService;
import com.masai.service.CategoryService;
import com.masai.service.OrdersService;
import com.masai.service.PaymentService;
import com.masai.service.ProduceService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ProduceService produceService;
	
	@Autowired
	private OrdersService orderService;
	

	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdminHandler(@Valid @RequestBody Admin admin) throws AdminException {
		Admin registered = adminService.adminRegister(admin);
		return new  ResponseEntity<Admin>(registered, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AdminSession> loginAdminHandler(@RequestBody LoginDTO loginDto) throws LogInException {
		AdminSession currentSession = adminService.adminLogin(loginDto);
		return new ResponseEntity<AdminSession>(currentSession, HttpStatus.CREATED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutAdminHandler(@RequestParam String key) throws LogInException {
		String logOutCurrentSession = adminService.logoutAdmin(key);
		return new ResponseEntity<String>(logOutCurrentSession, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminHandler(@Valid @RequestBody Admin admin, @PathVariable String username, @RequestParam String key ) throws LogInException, AdminException {
		Admin updatedAdmin = adminService.updateAdmin(admin, key);
		return new ResponseEntity<Admin>(updatedAdmin, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Admin> deleteAdminByIdHandler(@PathVariable Integer adminId, @RequestParam String key) throws LogInException, AdminException {
		Admin deletedAdmin = adminService.deleteAdminById(key);
		return new ResponseEntity<Admin>(deletedAdmin, HttpStatus.OK);
	}
	
	@PostMapping("/add/category")
	public ResponseEntity<Category> addCategoryHandler(@Valid @RequestBody Category category, @RequestParam String key) throws CategoryException, LogInException {
		Category addedCategory = categoryService.addNewCategory(category, key);
		return new  ResponseEntity<Category>(addedCategory, HttpStatus.OK);
	}
	
	@PutMapping("/update/category")
	public ResponseEntity<Category> updateCategoryHandler(@Valid @RequestBody Category category, @RequestParam String key) throws CategoryException, LogInException {
		Category updatedCategory = categoryService.updateCategory(category, key);
		return new  ResponseEntity<Category>(updatedCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/category/{categoryId}")
	public ResponseEntity<Category> deleteCategoryHandler(@PathVariable Integer categoryId, @RequestParam String key) throws CategoryException, LogInException {
		Category deletedCategory = categoryService.deleteCategory(categoryId, key);
		return new  ResponseEntity<Category>(deletedCategory, HttpStatus.OK);
	}
	
	@PostMapping("/add/payment")
	public ResponseEntity<Payment> addPaymentHandler(@Valid @RequestBody Payment payment, @RequestParam String key) throws LogInException, PaymentException {
		Payment addedPayment = paymentService.addNewPayment(payment, key);
		return new  ResponseEntity<Payment>(addedPayment, HttpStatus.OK);
	}
	
	@PutMapping("/update/payment")
	public ResponseEntity<Payment> updatePaymentHandler(@Valid @RequestBody Payment payment, @RequestParam String key) throws LogInException, PaymentException {
		Payment updatedPayment = paymentService.updatePayment(payment, key);
		return new ResponseEntity<Payment>(updatedPayment, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/payment/{paymentId}")
	public ResponseEntity<Payment> deletePaymentHandler(@PathVariable Integer paymentId, @RequestParam String key) throws LogInException, PaymentException {
		Payment deletedPayment = paymentService.deletePayment(paymentId, key);
		return new ResponseEntity<Payment>(deletedPayment, HttpStatus.OK);
	}
	
	@PatchMapping("/toggle/payemnt/{paymentId}")
	public ResponseEntity<Payment> togglePaymentHandler(@PathVariable Integer paymentId, @RequestParam String key) throws LogInException, PaymentException {
		Payment toggledPayment = paymentService.togglePaymentActiveSatus(paymentId, key);
		return new ResponseEntity<Payment>(toggledPayment, HttpStatus.OK);
	}
	
	@PostMapping("/add/produce")
	public ResponseEntity<Produce> addProduceHandler(@Valid @RequestBody Produce produce, @RequestParam String key) throws LogInException, ProduceException, UrlException {
		Produce addedProduce = produceService.addNewProduce(produce, key);
		return new ResponseEntity<Produce>(addedProduce, HttpStatus.OK);
	}
	
	@PutMapping("/update/produce")
	public ResponseEntity<Produce> updateProduceHandler(@Valid @RequestBody Produce produce, @RequestParam String key) throws LogInException, ProduceException, UrlException {
		Produce updatedProduce = produceService.updateProduce(produce, key);
		return new ResponseEntity<Produce>(updatedProduce, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/produce/{produceId}")
	public ResponseEntity<Produce> deleteProduceHandler(@PathVariable Integer produceId, @RequestParam String key) throws LogInException, ProduceException {
		Produce deletedProduce = produceService.deleteProduce(produceId, key);
		return new ResponseEntity<Produce>(deletedProduce, HttpStatus.OK);
	}
	
	@PatchMapping("/produce/addCategory/{produceId}/{categoryId}")
	public ResponseEntity<List<Category>> addCategoryToProduceHandler(@PathVariable Integer produceId, @PathVariable Integer categoryId,  @RequestParam String key) throws LogInException, ProduceException, CategoryException {
		List<Category> categoryList = produceService.addCategoryToProduce(produceId, categoryId, key);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@GetMapping("/all/orders")
	public ResponseEntity<List<Orders>> findAllOrdersHandler(@RequestParam String key) throws LogInException, OrdersException  {
		List<Orders> orderList = orderService.getAllOrders(key);
		return new ResponseEntity<List<Orders> >(orderList, HttpStatus.OK);
	}
	
}
