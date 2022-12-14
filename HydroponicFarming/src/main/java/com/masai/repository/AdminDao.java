package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {
	
	public Optional<Admin> findByAbstractUserUsername(String username);
	public Optional<Admin> findByAbstractUserMobile(String mobile);

}
