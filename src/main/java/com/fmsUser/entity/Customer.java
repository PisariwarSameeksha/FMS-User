package com.fmsUser.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
public class Customer extends User {

	@NotEmpty
	@NotNull
	@Pattern(regexp = "[a-zA-Z ]{3,}$", message = "Name can only contain alphabets, spaces and should have minimun 3 characters")
	private String name;

	@NotEmpty(message = "Email can not be empty")
	@NotNull
//	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+(com|in)$")
	private String email;

	@NotEmpty
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message = "password must atleast contain one uppercase, one lowercase, one digit, one special character and minimum length must be 8")
	private String password;

	@NotEmpty
	@NotNull
//	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile Number only starts with 6-9 ,Should have 10 Digits and it contains only numeric")
	@Pattern(regexp = "^[6-9]{1}?[0-9]{2,9}$", message = "Only digits are allowed. Mobile Number only starts with 6-9 ,min length is 3 and max length is 10")//After this first digit there should be atleast 2 digits and can be upto 9
	private String contactNo;

	@Past
	@NotNull
	private LocalDate dob;

	@NotNull
	@NotBlank(message = "Address is mandatory")
//	@Size(min = 3)
	@Pattern(regexp= "[a-zA-Z0-9, ]{5,}$", message = "alphabets, digits, white spaces, comma and hiphen are allowed")
	private String address;

	public Customer() {
		super();
	}

	public Customer(String name, String email, String userPassword, String userPhone, LocalDate dob, String address) {
		super();
		this.name = name;
		this.email = email;
		this.password = userPassword;
		this.contactNo = userPhone;
		this.dob = dob;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
