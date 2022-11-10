package com.masai.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CategoryException;
import com.masai.exception.LogInException;
import com.masai.exception.ProduceException;
import com.masai.exception.UrlException;
import com.masai.model.AdminSession;
import com.masai.model.Category;
import com.masai.model.Payment;
import com.masai.model.Produce;
import com.masai.model.UserSession;
import com.masai.repository.AdminSessionDao;
import com.masai.repository.CategoryDao;
import com.masai.repository.ProduceDao;
import com.masai.repository.UserSessionDao;

@Service
public class ProduceServiceImpl implements ProduceService {
	
	@Autowired
	private AdminSessionDao adminSessionDao;
	
	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private ProduceDao produceDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UrlValidationService urlValidationService;

	@Override
	public Produce addNewProduce(Produce produce, String adminKey) throws UrlException, LogInException, ProduceException {
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Produce> produceOpt = produceDao.findByProduceName(produce.getProduceName());
		
		if(!produceOpt.isEmpty()) 
			throw new ProduceException("Produce already exist with given name");
		
		if(!urlValidationService.isValidURL(produce.getImageUrl())) 
			throw new UrlException("Invalid url");
		
		return produceDao.save(produce);
	}

	@Override
	public Produce updateProduce(Produce produce, String adminkey) throws LogInException, ProduceException, UrlException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminkey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Produce> produceOpt = produceDao.findById(produce.getProduceId());
		
		if(produceOpt.isEmpty()) 
			throw new ProduceException("Produce not found");
		
		if(!urlValidationService.isValidURL(produce.getImageUrl())) 
			throw new UrlException("Invalid url");
		
		return produceDao.save(produce);
	}

	@Override
	public Produce deleteProduce(Integer produceId, String adminkey) throws LogInException, ProduceException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminkey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Produce> produceOpt = produceDao.findById(produceId);
		
		if(produceOpt.isEmpty()) 
			throw new ProduceException("Produce not found");
		
		produceDao.delete(produceOpt.get());
		return produceOpt.get();
		
	}

	@Override
	public List<Category> addCategoryToProduce(Integer produceId, Integer categoryId, String adminkey) throws LogInException, ProduceException, CategoryException {

		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminkey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Produce> produceOpt = produceDao.findById(produceId);
		
		if(produceOpt.isEmpty()) 
			throw new ProduceException("Produce not found");
		
		Optional<Category> categoryOpt = categoryDao.findById(categoryId);
		
		if(categoryOpt.isEmpty())
			throw new CategoryException("Category not found");
		
		Produce produce = produceOpt.get();
		Category category = categoryOpt.get();
		
		HashSet<Category> categorySet = new HashSet<Category>();
		
		List<Category> categoryList = produce.getCategoryList();
		
		for(Category c:categoryList) {
			categorySet.add(c);
		}
		categorySet.add(category);
		
		List<Category> newCategoryList = new ArrayList<Category>();
		
		for(Category c:categorySet) {
			newCategoryList.add(c);
		}
		
		produce.setCategoryList(newCategoryList);
		
		produceDao.save(produce);
		return produce.getCategoryList();
	}

	@Override
	public List<Produce> getAllProduce(String key) throws LogInException, ProduceException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(key);
		
		
		Optional<UserSession> userSessionOpt = userSessionDao.findByUuId(key);
		
		if(adminSessionOpt.isEmpty() && userSessionOpt.isEmpty())
			throw new LogInException("User is not logged in");
		
		List<Produce> produceList = produceDao.findAll();
		
		if(produceList.isEmpty())
			throw new ProduceException("No produce found");
		
		return produceList;
	}

}
