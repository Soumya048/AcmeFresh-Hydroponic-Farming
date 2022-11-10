package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
	
	Optional<Category> findByCategoryName(String categoryName);
	

}
