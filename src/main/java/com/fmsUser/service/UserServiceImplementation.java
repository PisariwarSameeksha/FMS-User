package com.fmsUser.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fmsUser.DTO.UserDTO;
import com.fmsUser.entity.Login;
import com.fmsUser.entity.Users;
import com.fmsUser.exception.LoginException;
import com.fmsUser.exception.UserException;
import com.fmsUser.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Users addUser(UserDTO newUser) throws UserException{
		Optional<Users> getUserByEmail=userRepository.findByEmail(newUser.getEmail());
		if(getUserByEmail.isPresent()) {
			throw new UserException("can't create new user as email is already existed");
		}
		String mail=newUser.getEmail().toLowerCase();
		newUser.setEmail(mail);
		Users user=modelMapper.map(newUser, Users.class);
		return userRepository.save(user);
	}

//	@Override
//	public Users updateUser(Users updateUser) throws UserException{
//		Optional<Users> getUserById=userRepository.findById(updateUser.getUserId());
//		if(getUserById.isEmpty())
//		{
//			throw new UserException("Cannot Update User Details As User Id not found");
//		}
//		Optional<Users> getUserByEmail=userRepository.findByEmail(updateUser.getEmail());
//		if(getUserByEmail.isPresent()&& (!getUserByEmail.get().getUserId().equals(updateUser.getUserId())))
//		{
//				throw new UserException("Cannot Update User Details As Email Already Exists");
//		}
//		return userRepository.save(updateUser);
//	}
	
//	@Override
//	public Users updateUser(Integer userId, Users updateUser) throws UserException{
//		Optional<Users> getUserById=userRepository.findById(updateUser.getUserId());
//		if(getUserById.isEmpty())
//		{
//			throw new UserException("Cannot Update User Details As User Id not found");
//		}
////		Optional<Users> getUserByEmail=userRepository.findByEmail(updateUser.getEmail());
////		if(getUserByEmail.isPresent()&& (!getUserByEmail.get().getUserId().equals(updateUser.getUserId())))
////		{
////				throw new UserException("Cannot Update User Details As Email Already Exists");
////		}
//		return userRepository.save(updateUser);
//	}
	
	@Override
	public Users updateUser(Long userId, Users updateUser) throws UserException{
		Optional<Users> getUserById=userRepository.findById(userId);
		if(getUserById.isEmpty())
		{
			throw new UserException("Cannot Update User Details As User Id not found");
		}
		else {
			Optional<Users> getUserByEmail=userRepository.findByEmail(updateUser.getEmail());
			if(getUserByEmail.isPresent()&& (!getUserByEmail.get().getUserId().equals(userId)))
			{
					throw new UserException("Cannot Update User Details As Email Already Exists");
			}
			updateUser.setUserId(userId);
			updateUser.setUserName(updateUser.getUserName());
			updateUser.setUserPassword(updateUser.getUserPassword());
			updateUser.setUserPhone(updateUser.getUserPhone());
			updateUser.setEmail(updateUser.getEmail().toLowerCase());
		}
		return userRepository.save(updateUser);
	}


	@Override
	public Users getUserById(Long userId) throws UserException {
		Optional<Users> getUser=userRepository.findById(userId);
		if(getUser.isEmpty()) {
			throw new UserException("given user id is not Found");
		}
		return getUser.get();
	}

	@Override
	public Users deleteUserById(Long userId) throws UserException {
		Optional<Users> getUser=userRepository.findById(userId);
		if(getUser.isEmpty()) {
			throw new UserException("Invalid User Id");
		}
		userRepository.deleteById(userId);
		return getUser.get();
	}

	@Override
	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}
	
	@Override
	public List<Users> getUsersByUserType(String userType)throws UserException{
		List<Users> usersList=userRepository.findByUserType(userType);
		if(usersList.isEmpty()) {
			throw new UserException("There are no users of type:"+userType);
		}
		return usersList;
	}
	
	@Override
	public String login(Login login) throws LoginException {
		Optional<Users> customer=this.userRepository.findByEmail(login.getUsername());
		if(customer.isEmpty()) {
			throw new LoginException("User doesn't exist with the given userName and Password.");
		}
		if(!customer.get().getUserPassword().equals(login.getPassword())) {
			throw new LoginException("Password is wrong.");
		}
		String issuer=customer.get().getEmail();
		Date expiry = new Date(System.currentTimeMillis()+(60*60*1000));
		
		return Jwts.builder().setIssuer(issuer).setExpiration(expiry).signWith(SignatureAlgorithm.HS512, "Secret123").compact();
	}
	
	public List<Users> findUserWithSorting(String field){
		return userRepository.findAll(Sort.by(Sort.Direction.ASC,field));
	}
}
