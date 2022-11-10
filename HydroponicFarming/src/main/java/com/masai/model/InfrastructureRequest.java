package com.masai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfrastructureRequest {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Integer requestId;

	@NotNull
	private Integer userId;
	
	@Pattern(regexp = "[6789]{1}[0-9]{10}", message = "Enter valid 10 digit mobile number")
	private String contactNumber;
	
	@Embedded
	private Address address;
	
	private LocalDateTime requestDateTime = LocalDateTime.now();

	private LocalDate estimatedDate = LocalDate.now().plusDays(7);
	
	@JsonIgnore
	private LocalDateTime completedDate;
	
	private Boolean isCompleted = false;
	
	private Boolean isCancelled = false;
	
}
