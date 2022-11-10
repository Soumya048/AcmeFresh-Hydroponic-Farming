package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	
	@NotNull
	private Integer produceId;

	@NotNull
	private Integer paymentId;
	
	@NotNull
	private Integer quantity;
	
	@JsonIgnore
	private Double totalPrice;
	
	private LocalDateTime orderDateTime = LocalDateTime.now();
	
	private LocalDateTime deliveryDate = LocalDateTime.now().plusDays(7);
	
	@Embedded
	private Address address;
	
	private Boolean isCancelled = false;
	
}
