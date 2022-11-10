package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.AutomateFarmRequest;

@Repository
public interface AutomateFarmRequestDao extends JpaRepository<AutomateFarmRequest, Integer> {

}
