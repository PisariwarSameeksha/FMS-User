package com.fmsUser.exception;

public class JwtTokenMissingException extends Exception {

	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}