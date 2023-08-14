package com.fmsUser.service;

import java.util.List;

import com.fmsUser.entity.Customer;
import com.fmsUser.exception.CustomerException;

public interface CustomerService {
	Customer addUser(Customer addUser) throws CustomerException;

	Customer updateUser(Long userId, Customer updateUser) throws CustomerException;

	Customer getUserById(Long userId) throws CustomerException;

	Customer deleteUserById(Long userId) throws CustomerException;

	List<Customer> getAllUsers();

//	Customer changePassword(Long id, String password);

	Customer changePassword(String email, String password) throws CustomerException;

}
