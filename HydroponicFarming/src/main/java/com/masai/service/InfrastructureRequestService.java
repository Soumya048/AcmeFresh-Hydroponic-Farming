package com.masai.service;

import com.masai.exception.InfrastructureRequestException;
import com.masai.exception.LogInException;
import com.masai.model.InfrastructureRequest;

public interface InfrastructureRequestService {
	
	public InfrastructureRequest requestInfrastructure(InfrastructureRequest irequest, String userKey) throws LogInException, InfrastructureRequestException;
	public InfrastructureRequest completedInfrastructureRequest(Integer requestId, String adminkey) ;
	public InfrastructureRequest cancelInfrastructureRequest(Integer requestId, String userKey) ;
}
