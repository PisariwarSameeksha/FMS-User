package com.fmsUser.DTO;

import com.fmsUser.entity.UserType;

public class UserDTO {
	private Long userId;
	private String userName;
	private String userPassword;
	private String userPhone;
	private String email;
	private UserType userType;
	 
	public UserDTO() {
		super();
	}
	
	public UserDTO(String userName, String userPassword, String userPhone,
			String email, UserType userType) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.email = email;
		this.userType=userType;
	}
	public UserDTO(Long userId, String userName, String userPassword, String userPhone,
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
