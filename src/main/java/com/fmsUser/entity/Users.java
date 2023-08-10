package com.fmsUser.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Users{
	
	@Id
	@GeneratedValue
	private Long userId;
	
	@NotEmpty
	@NotNull
	@Pattern(regexp="[a-zA-Z]{3,}",message="Name can only Contain Alphabet and Should have minimun 3 characters")
	private String userName;
	
	@NotEmpty
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message="password must atleast contain one uppercase, one lowercase, one digit, one special character and minimum length must be 8")
	private String userPassword;
	
	@NotEmpty
	@NotNull
    @Pattern(regexp="^[6-9]\\d{9}$",message="Mobile Number only starts with 6-9 ,Should have 10 Digits and it contains only numeric")
	private String userPhone;
	
	@NotEmpty(message = "Email cannot be empty")
	@NotNull
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserType userType;
	 
	public Users() {
		super();
	}
	
	public Users(String userName, String userPassword, String userPhone,
			String email, UserType userType) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.email = email;
		this.userType=userType;
	}
	public Users(Long userId, String userName, String userPassword, String userPhone,
			String email,UserType userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.email = email;
		this.userType=userType;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}


