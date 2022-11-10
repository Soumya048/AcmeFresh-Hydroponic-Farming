package com.masai.service;

import java.util.List;

import com.masai.exception.CategoryException;
import com.masai.exception.LogInException;
import com.masai.exception.ProduceException;
import com.masai.exception.UrlException;
import com.masai.model.Category;
import com.masai.model.Produce;

public interface ProduceService {
	
	
	public Produce addNewProduce(Produce produce, String adminKey) throws LogInException, ProduceException, UrlException;
	public Produce updateProduce(Produce produce, String adminkey) throws LogInException, ProduceException, UrlException;
	public Produce deleteProduce(Integer produceId, String adminkey) throws LogInException, ProduceException;
	public List<Produce> getAllProduce(String key) throws LogInException, ProduceException;
	public List<Category> addCategoryToProduce(Integer produceId, Integer categoryId, String adminkey) throws LogInException, ProduceException, CategoryException;
	

}
