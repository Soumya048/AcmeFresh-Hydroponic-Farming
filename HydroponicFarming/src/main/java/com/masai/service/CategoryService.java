package com.masai.service;

import com.masai.exception.CategoryException;
import com.masai.exception.LogInException;
import com.masai.model.Category;

public interface CategoryService {
	
	public Category addNewCategory(Category category, String adminKey) throws CategoryException, LogInException;
	public Category updateCategory(Category category, String adminKey) throws CategoryException, LogInException;;
 	public Category deleteCategory(Integer categoryId, String adminKey) throws CategoryException, LogInException;
	
}
