package com.masai.service;

import com.masai.dto.LoginDTO;
import com.masai.exception.AdminException;
import com.masai.exception.LogInException;
import com.masai.model.Admin;
import com.masai.model.AdminSession;

public interface AdminService {
	
	public Admin adminRegister(Admin admin) throws AdminException;
	public AdminSession adminLogin(LoginDTO loginDto) throws LogInException;
	public Admin updateAdmin(Admin admin, String key) throws LogInException, AdminException ;
	public Admin deleteAdminById(String key) throws LogInException, AdminException;
	public String logoutAdmin(String key) throws LogInException;

}
