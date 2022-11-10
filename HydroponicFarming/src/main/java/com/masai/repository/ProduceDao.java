package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Produce;

@Repository
public interface ProduceDao extends JpaRepository<Produce, Integer> {

	Optional<Produce> findByProduceName(String produceName);
	
}
