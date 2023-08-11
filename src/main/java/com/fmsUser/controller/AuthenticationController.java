package com.fmsUser.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmsUser.JwtUtil.JwtUtil;
import com.fmsUser.entity.Login;
import com.fmsUser.entity.User;
import com.fmsUser.exception.InvalidUserException;
import com.fmsUser.repository.UserRepository;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

	@Autowired
	UserRepository userRepository;

	User user;

	@PostMapping
	public ResponseEntity<User> createAuthenticationToken(@RequestBody Login request, HttpServletResponse response)
			throws InvalidUserException {
		user = userRepository.findByUserName(request.getUsername());
		if (user == null) {
			throw new InvalidUserException("User not found with username: " + request.getUsername());
		}
		if (!(user.getPassword().equals(request.getPassword())))
			throw new InvalidUserException("Invalid Password");
		String token = JwtUtil.generateToken(user.getUserId().toString());
		response.setHeader("Authorization", token);
		response.addHeader("token", token);
		response.addHeader("Access-Control-Expose-Headers", "token");
		return ResponseEntity.ok().body(user);
	}
}
