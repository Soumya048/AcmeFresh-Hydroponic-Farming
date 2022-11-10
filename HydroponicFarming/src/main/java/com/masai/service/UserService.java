package com.masai.service;

import com.masai.dto.LoginDTO;
import com.masai.exception.LogInException;
import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.model.UserSession;

public interface UserService {

	public User registerUser(User user) throws UserException;                                                     
	public UserSession loginUser(LoginDTO user) throws LogInException, UserException ;
	public String logoutUser(String key) throws UserException;
	public User updateUser(User user, String key) throws UserException, LogInException;                                          
	public User deleteUser(String key) throws UserException, LogInException;  
	
}
