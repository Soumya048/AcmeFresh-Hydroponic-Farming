package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produce {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer produceId;
	
	@NotNull
	private String produceName;
	
	@NotNull
	private String imageUrl;
	
	@NotNull
	@Size(max = 250, message = "maximum length is 250")
	private String about;
	
	@NotNull
	private String farmName;
	
	@NotNull
	private Double salePrice;
	
	@NotNull
	private Integer availableQuantity;
	
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Category> categoryList = new ArrayList<Category>();
	
}
