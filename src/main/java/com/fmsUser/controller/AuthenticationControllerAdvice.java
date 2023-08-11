package com.fmsUser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fmsUser.exception.InvalidUserException;

@RestControllerAdvice
public class AuthenticationControllerAdvice {

	@ExceptionHandler(InvalidUserException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleInvalidUserException(InvalidUserException exception) {
		return exception.getMessage();
	}
}
