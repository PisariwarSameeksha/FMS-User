package com.fmsUser.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	@Pattern(regexp= "^[a-zA-Z0-9_-]{8,15}$", message = "User name can contain alphabets, digits, _ , - and min length is 8 and max length is 15.")
	private String userName;
	private String password;
	private String role;

	public User() {

	}

	public User(String userName, String password, String role) {
		super();

		this.userName = userName;
		this.password = password;
		this.role = role;

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", role=" + role + "]";
	}
}
