package com.masai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CategoryException;
import com.masai.exception.LogInException;
import com.masai.model.AdminSession;
import com.masai.model.Category;
import com.masai.repository.AdminSessionDao;
import com.masai.repository.CategoryDao;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private AdminSessionDao adminSessionDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	
	@Override
	public Category addNewCategory(Category category, String adminKey) throws CategoryException, LogInException {
		
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Category> categoryOpt = categoryDao.findByCategoryName(category.getCategoryName());
		
		if(!categoryOpt.isEmpty())
			throw new CategoryException("Category already exist");
		
		return categoryDao.save(category);
	}

	@Override
	public Category updateCategory(Category category, String adminKey) throws CategoryException, LogInException {
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Category> categoryOpt = categoryDao.findById(category.getCategoryId());
		
		if(categoryOpt.isEmpty())
			throw new CategoryException("Category does not exist");
		
		return categoryDao.save(category);
	}

	@Override
	public Category deleteCategory(Integer categoryId, String adminKey) throws CategoryException, LogInException {
		Optional<AdminSession> adminSessionOpt = adminSessionDao.findByUuId(adminKey);
		
		if(adminSessionOpt.isEmpty())
			throw new LogInException("Admin is not logged in");
		
		Optional<Category> categoryOpt = categoryDao.findById(categoryId);
		
		if(categoryOpt.isEmpty())
			throw new CategoryException("Category does not exist");
		
		categoryDao.delete(categoryOpt.get());
		
		return categoryOpt.get();
	}

}
