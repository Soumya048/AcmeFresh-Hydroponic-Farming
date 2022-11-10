package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.LoginDTO;
import com.masai.exception.AdminException;
import com.masai.exception.LogInException;
import com.masai.model.Admin;
import com.masai.model.AdminSession;
import com.masai.repository.AdminDao;
import com.masai.repository.AdminSessionDao;

@Service
public class AdminServiceImp implements AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private AdminSessionDao adminSessionDao;
	
	@Autowired
	private UrlValidationService urlValidationService;

	@Override
	public Admin adminRegister(Admin admin) throws AdminException {
		
		String adminMobile = admin.getAbstractUser().getMobile();
		
		Optional<Admin> opt = adminDao.findByAbstractUserMobile(adminMobile);
		
		if(opt.isPresent())
			throw new AdminException("Admin already exist with" + adminMobile);
		
		Admin registeredAdmin = adminDao.save(admin);
		
		return registeredAdmin;
	}

	@Override
	public AdminSession adminLogin(LoginDTO loginDto) throws LogInException {

		Optional<Admin> opt = adminDao.findByAbstractUserMobile(loginDto.getMobile());
		
		
		if(!opt.isPresent()) 
			throw new LogInException("Admin Not found");
		
		
		Admin existingAdmin = opt.get();
		Optional<AdminSession> sessionOpt = adminSessionDao.findByAdminId(existingAdmin.getAdminId());
		
		if(sessionOpt.isPresent()) 
			throw new LogInException("User already logged in");
		
		
		if(existingAdmin.getAbstractUser().getPassword().equals(loginDto.getPassword())) {
			
			UUID randomUUID = UUID.randomUUID();
			String key = randomUUID.toString().replaceAll("-", "");
			
			
			AdminSession newAdminSession = new AdminSession();
			
			newAdminSession.setAdminId(existingAdmin.getAdminId());
			newAdminSession.setUuId(key);
			newAdminSession.setUserType("Admin");
			newAdminSession.setSessionStartTime(LocalDateTime.now());
			newAdminSession.setSessionEndTime(LocalDateTime.now().plusHours(2));
			return adminSessionDao.save(newAdminSession);
		}
		else
			throw new LogInException("Password Incorrect, Please Try Again");
	}

	@Override
	public Admin updateAdmin(Admin admin, String key) throws LogInException, AdminException {
		
		Admin updated = null;
		Optional<AdminSession> AdminSessionOpt = adminSessionDao.findByUuId(key);
		
		if(AdminSessionOpt.isPresent()) {
			
			AdminSession adminSession = AdminSessionOpt.get();
				
			Integer id = adminSession.getAdminId();
			
			Admin newData = new Admin();
				
			newData.setAdminId(id);
			newData.setAbstractUser(admin.getAbstractUser());
				
			updated = adminDao.save(newData);
			
		}
		else 
			throw new LogInException("Admin is not logged in, Please Login first");
		
		return updated;
		
	}

	@Override
	public Admin deleteAdminById(String key) throws LogInException, AdminException {
		
		Admin existingAdmin = null;
		
		Optional<AdminSession> AdminSessionOpt = adminSessionDao.findByUuId(key);
		
		if(AdminSessionOpt.isPresent()) {
				existingAdmin = adminDao.findById(AdminSessionOpt.get().getAdminId()).get();
				adminSessionDao.delete(AdminSessionOpt.get());
				adminDao.delete(existingAdmin);
		}
		else 
			throw new LogInException("Admin is not logged in, Please Login first");
		
		return existingAdmin;
	}

	@Override
	public String logoutAdmin(String key) throws LogInException {
		
		Optional<AdminSession> opt = adminSessionDao.findByUuId(key);

		if(!opt.isPresent()) 
			throw new LogInException("Not Logged in, Log in first");
		
		adminSessionDao.delete(opt.get());
		
		return "Log out Successful";
	}


}
