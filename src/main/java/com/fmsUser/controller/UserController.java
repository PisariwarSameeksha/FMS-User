package com.fmsUser.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmsUser.DTO.UserDTO;
import com.fmsUser.entity.Login;
import com.fmsUser.entity.Users;
import com.fmsUser.exception.LoginException;
import com.fmsUser.exception.UserException;
import com.fmsUser.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {
		
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<String> addUser(@RequestBody @Valid UserDTO newUser)throws UserException{
		Users user=userService.addUser(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Id: "+user.getUserId()+" Details added successfully");
	}
	
//		@PutMapping("/")
//		public String updateUser(@RequestBody @Valid Users updateuser)throws UserException{
//			Users updateUser=userService.updateUser(updateuser);
//			return "User Id: "+updateUser.getUserId()+" Details updated successfully";
//		}
	
	@PutMapping("/{userId}")
	public String updateUserById(@PathVariable("userId") Long userId, @RequestBody @Valid Users updateUser)throws UserException{
		Users existingUser=userService.updateUser(userId, updateUser);
		BeanUtils.copyProperties(updateUser, existingUser,"userId");
		return "User Id: "+existingUser.getUserId()+" Details updated successfully";
	}
	
	@GetMapping("/{id}")
	public Users getUserById(@PathVariable("id") Long userId) throws UserException {
		return userService.getUserById(userId);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUserById(@PathVariable("userId") Long userId)throws UserException{
		Users user=userService.deleteUserById(userId);
		return "User Id: "+user.getUserId()+" was deleted successfully";
	}
	
//		@GetMapping("/users")
//		public List<Users> getAllUsers(@CookieValue("jwt") String jwt) throws UserException,JwtTokenMalformedException, JwtTokenMissingException{
//			if(jwt == null)
//				throw new UserException("Unauthenticated !");
//			JwtUtil.validateToken(jwt);
//			return userService.getAllUsers();
////			List<Users> users= userService.getAllUsers();
////			return new ApiResponse<>(users.size(), users);
//		}
	
	@GetMapping("/")
	public List<Users> getAllUsers(){

		return userService.getAllUsers();
	}
	
//	@GetMapping("/type/{userType}")
//	public List<Users> getUsersByUserType(@PathVariable("userType") String userType) throws UserException{
//		return this.userService.getUsersByUserType(userType);
//	}
	
	@PostMapping("/login")
	public String login(@RequestBody Login login, HttpServletResponse responce) throws LoginException{
		Cookie cookie = new Cookie("jwt", this.userService.login(login));
		responce.addCookie(cookie);
		return "Login Success!";
	}
	@PostMapping("/logout")
	public String logout(HttpServletResponse response) {
		Cookie cookie=new Cookie("jwt","");
		response.addCookie(cookie);
		return "Logout Success";
	}
}

