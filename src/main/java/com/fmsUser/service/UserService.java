package com.fmsUser.service;

import java.util.List;

import com.fmsUser.DTO.UserDTO;
import com.fmsUser.entity.Login;
import com.fmsUser.entity.Users;
import com.fmsUser.exception.LoginException;
import com.fmsUser.exception.UserException;

public interface UserService {
	Users addUser(UserDTO addUser) throws UserException;
	
	Users updateUser(Long userId,Users updateUser)throws UserException;
	
	Users getUserById(Long userId)throws UserException;
	
	Users deleteUserById(Long userId)throws UserException;
	
	List<Users> getAllUsers();

	String login(Login login) throws LoginException;

	List<Users> getUsersByUserType(String userType) throws UserException;

	
}
