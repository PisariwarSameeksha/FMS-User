package com.fmsUser.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmsUser.entity.Customer;
import com.fmsUser.exception.CustomerException;
import com.fmsUser.service.CustomerService;

@RestController
//@Validated
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@PostMapping("/")
	public ResponseEntity<String> addUser(@RequestBody @Valid Customer newUser) throws CustomerException {
		try {
			logger.info("Received request to add new user: {}", newUser);
			Customer user = customerService.addUser(newUser);
			logger.info("User added: {}", newUser);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("User Id: " + user.getUserId() + " details added successfully");
		} catch (CustomerException e) {
			logger.warn("can't create new user as email id: {} is already existed", newUser.getEmail());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUserById(@PathVariable("userId") Long userId,
			@RequestBody @Valid Customer updateUser) throws CustomerException {
		try {
			logger.info("Received request to modify with userId: {}", userId);
			Customer existingUser = customerService.updateUser(userId, updateUser);
			logger.info("User details having userId: {} modified successfully", userId);
			return ResponseEntity.status(HttpStatus.OK)
					.body("User Id: " + existingUser.getUserId() + " details updated successfully");
		} catch (CustomerException e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getUserById(@PathVariable("id") Long userId, HttpServletRequest request)
			throws CustomerException {
		logger.info("Received request to fetch details for given user id: {}", userId);
		Customer user = customerService.getUserById(userId);
		logger.info("User details fetched successfully for the given user id: {}", userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long userId, HttpServletRequest request)
			throws CustomerException {
		try {
			logger.info("Received request to remove user having User Id: {}", userId);
			Customer user = customerService.deleteUserById(userId);
			logger.info("User having user id: {} removed successfully", userId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("User Id: " + user.getUserId() + " was deleted successfully");
		} catch (CustomerException e) {
			logger.warn("User with user id {} is not found", userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Customer>> getAllUsers() {
		logger.info("Received request to fetch all users");
		List<Customer> userList = customerService.getAllUsers();
		logger.info("fetched {} users", userList.size());
		return ResponseEntity.status(HttpStatus.OK).body(userList);
	}
	
	@PutMapping("/forgotPassword/{email}")
	public Customer changePassword(@PathVariable("email") String email, @RequestBody String password) throws CustomerException {
		return this.customerService.changePassword(email, password);
	}
}
