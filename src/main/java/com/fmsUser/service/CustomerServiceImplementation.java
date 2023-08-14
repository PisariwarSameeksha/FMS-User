package com.fmsUser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmsUser.entity.Customer;
import com.fmsUser.entity.User;
import com.fmsUser.exception.CustomerException;
import com.fmsUser.repository.CustomerRepository;
import com.fmsUser.repository.UserRepository;

@Service
public class CustomerServiceImplementation implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Customer addUser(Customer newUser) throws CustomerException {
		Optional<User> user = Optional.ofNullable(userRepository.findByUserName(newUser.getUserName()));
		if (user.isPresent()) {
			throw new CustomerException("can't create new user as email is already existed");
		}
		String mail = newUser.getEmail().toLowerCase();
		newUser.setEmail(mail);
		return customerRepository.save(newUser);
	}

	@Override
	public Customer updateUser(Long userId, Customer updateUser) throws CustomerException {
		Optional<Customer> getUserById = customerRepository.findById(userId);
		if (getUserById.isEmpty()) {
			throw new CustomerException("Cannot Update User Details As User Id not found");
		} else {
			Optional<Customer> getUserByEmail = customerRepository.findByEmail(updateUser.getEmail());
			if (getUserByEmail.isPresent() && (!getUserByEmail.get().getUserId().equals(userId))) {
				throw new CustomerException("Cannot Update User Details As Email Already Exists");
			}
			updateUser.setUserId(userId);
			updateUser.setName(updateUser.getName());
			updateUser.setEmail(updateUser.getEmail().toLowerCase());
			updateUser.setPassword(updateUser.getPassword());
			updateUser.setContactNo(updateUser.getContactNo());
			updateUser.setDob(updateUser.getDob());
			updateUser.setAddress(updateUser.getAddress());

		}
		return customerRepository.save(updateUser);
	}

	@Override
	public Customer getUserById(Long userId) throws CustomerException {
		Optional<Customer> getUser = this.customerRepository.findById(userId);
		if (getUser.isEmpty()) {
			throw new CustomerException("Given user id is not Found");
		}
		return getUser.get();
	}

	@Override
	public Customer deleteUserById(Long userId) throws CustomerException {
		Optional<Customer> getUser = customerRepository.findById(userId);
		if (getUser.isEmpty()) {
			throw new CustomerException("User Id not found.");
		}
		customerRepository.deleteById(userId);
		return getUser.get();
	}

	@Override
	public List<Customer> getAllUsers() {
		return customerRepository.findAll();
	}

//	@Override
//	public Customer changePassword(Long id, String password) {
//		if (customerRepository.existsById(id)) {
//			Customer customer=customerRepository.findById(id).get();
//			customer.setPassword(password);
//			customerRepository.save(customer);
//			return customer;
//		}
//		else {
//			return null;
//		}
//	}

	@Override
	public Customer changePassword(String email, String password) throws CustomerException {
		Optional<Customer> customer = customerRepository.findByEmail(email);
		if (customer.isEmpty()) {
			throw new CustomerException("User does not exist with the given email id");
		}

		Customer customer1 = customerRepository.findByEmail(email).get();
		customer1.setPassword(password);
		customerRepository.save(customer1);
		return customer1;

	}

}
