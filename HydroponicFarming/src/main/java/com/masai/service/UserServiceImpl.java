package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.masai.dto.LoginDTO;
import com.masai.exception.LogInException;
import com.masai.exception.UserException;
import com.masai.model.User;
import com.masai.model.UserSession;
import com.masai.repository.UserDao;
import com.masai.repository.UserSessionDao;

@Service
public class UserServiceImpl implements UserService  {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	 
	@Value("${spring.mail.username}") private String sender;
	
	

	@Override
	public User registerUser(User user) throws UserException {
		
		Optional<User> existing = userDao.findByAbstractUserMobile(user.getAbstractUser().getMobile());

		if (existing.isPresent()) 
			throw new UserException("A Customer already exist with this mobile number in the Database");
		
		return userDao.save(user); 
		 
	}


	@Override
	public UserSession loginUser(LoginDTO user) throws LogInException, UserException {
		 
		 Optional<User> userOpt = userDao.findByAbstractUserMobile(user.getMobile());

		if (!userOpt.isPresent()) 
			throw new UserException("User does not exist with the given mobile number");

		User existingUser = userOpt.get();
		
		Optional<UserSession> usOpt = userSessionDao.findByUserId(existingUser.getUserId());

		if (usOpt.isPresent())
			throw new LogInException("User already logged in");

		if (existingUser.getAbstractUser().getPassword().equals(user.getPassword())) {

			UserSession newSession = new UserSession();

			newSession.setUserId(existingUser.getUserId());
			newSession.setUserType("User");
			newSession.setSessionStartTime(LocalDateTime.now());
			newSession.setSessionEndTime(LocalDateTime.now().plusHours(2));

			UUID uuid = UUID.randomUUID();
			String key = uuid.toString().split("-")[0];

			newSession.setUuId(key);
			

			return userSessionDao.save(newSession);
		} 
		else 
			throw new LogInException("Password Incorrect. Try again.");
		
	}

	@Override
	public String logoutUser(String key) throws UserException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");

		userSessionDao.delete(usOpt.get());

		return "User has succefully logged out.";
	}

	@Override
	public User updateUser(User user, String key) throws UserException, LogInException {
		
		User update = null;
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");
		else {
		    	 
			UserSession userSession = usOpt.get();
			Integer id = userSession.getUserId();
			
			User newData = new User();
			newData.setUserId(id);
			newData.setAbstractUser(user.getAbstractUser());
				
			update = userDao.save(newData);
			return update;
		}
	}

	@Override
	public User deleteUser(String key) throws UserException, LogInException {

		User existingCustomer = null;
	
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");
		
		UserSession userSession = usOpt.get();
		
		Optional<User> custOtp = userDao.findById(userSession.getUserId());

		existingCustomer = custOtp.get();
		
		userDao.delete(existingCustomer);
		userSessionDao.delete(userSession);
		
		return existingCustomer;
	}

}
