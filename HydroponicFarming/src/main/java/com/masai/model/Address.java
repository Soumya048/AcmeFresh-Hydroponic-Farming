package com.masai.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@NotNull
	private String landmark;
	
	@NotNull
	private String city;
	
	@NotNull
	private String state;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
	private String pincode;
	
}
