package com.masai.service;

import org.springframework.stereotype.Service;

import com.masai.exception.InfrastructureRequestException;
import com.masai.exception.LogInException;
import com.masai.model.InfrastructureRequest;

@Service
public class InfrastructureRequestServiceImpl  implements InfrastructureRequestService {

	@Override
	public InfrastructureRequest requestInfrastructure(InfrastructureRequest irequest, String userKey)
			throws LogInException, InfrastructureRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfrastructureRequest completedInfrastructureRequest(Integer requestId, String adminkey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfrastructureRequest cancelInfrastructureRequest(Integer requestId, String userKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
