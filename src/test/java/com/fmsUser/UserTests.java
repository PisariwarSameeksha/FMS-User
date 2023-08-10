package com.fmsUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fmsUser.entity.UserType;
import com.fmsUser.entity.Users;
import com.fmsUser.exception.UserException;
import com.fmsUser.service.UserService;

@SpringBootTest
class UserTest {
	
	@Autowired
	private UserService userService;
	
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
//	@Test
//	void testAddUser() throws UserException{
//		assertNotNull(userService.addUser(new Users(500L,"Divya@gmail","Divya","Divya@12","6600598851",UserType.CUSTOMER)));
//	}
//	@Test
//	void testAddUserEmailException()throws UserException{
//		assertThrows(UserException.class,()->userService.addUser(new Users(30L,"Sindhu@gmail","Akhila","Akhila@12","6688641930",UserType.CUSTOMER)));
//	}
//	
//	@Test
//	void testAddUserEmailExceptionMsg() throws UserException{
//		try {
//			userService.addUser(new Users(900L,"Sindhu@gmail","Shravya","Shravya@12","7474382847",UserType.CUSTOMER));
//		}
//		catch(UserException exception){
//			String str="can't create new user as email is already existed";
//			assertEquals(str,exception.getMessage());
//		}
//	}
//	
//	@Test
//	void testUpdateUser() throws UserException{
//		assertNotNull(userService.updateUser(16L,new Users("Nikhila@gmail","Nikhila","Nikhila@12","7666641930",UserType.CUSTOMER)));
//	}
//	
//	@Test
//	void testUpdateUserException() throws UserException{
//		assertThrows(UserException.class,()->userService.updateUser(100L, new Users("Sindhu@gmail","Sindhu","Sindhu@12","6666641930",UserType.CUSTOMER)));
//	}
//	
//	@Test
//	void testUpdateUserIdException() throws UserException{
//		try {
//			userService.updateUser(100L,new Users("Sindhu@gmail","Sindhu112","Sindhu@12","6666641930",UserType.CUSTOMER));
//		}
//		catch(UserException exception) {
//			String str="Cannot Update User Details As User Id not found";
//			assertEquals(str,exception.getMessage());
//		}
//	}
//	
//	@Test
//	void testUpdateUserEmailException() throws UserException{
//		try {
//			userService.updateUser(30L,new Users("Sindhu@gmail","Sindhu112","Sindhu@12","6666641930",UserType.CUSTOMER));
//		}
//		catch(UserException exception) {
//			String str="Cannot Update User Details As Email Already Exists";
//			assertEquals(str,exception.getMessage());
//		}
//	}
	
	@Test
	void testGetUserById() throws UserException{
		assertNotNull(userService.getUserById(16L));
	}
	
	@Test
	void testGetUserByIdException() {
		assertThrows(UserException.class,()->userService.getUserById(100L));
	}
	
	@Test
	void testGetUserByIdExceptionMsg() throws UserException{
		try {
			userService.getUserById(200L);
		}
		catch(UserException exception) {
			String str="given user id is not Found";
			assertEquals(str,exception.getMessage());
		}
	}

	@Test
	void testDeleteUserById() throws UserException{
		assertNull(userService.deleteUserById(40L));
	}
	
	@Test 
	void testDeleteUserByIdException() throws UserException{
		assertThrows(UserException.class,()->userService.getUserById(100L));
	}
	
	@Test
	void testDeleteUserByIdExceptionMsg() throws UserException{
		try {
			userService.deleteUserById(1000L);
		}
		catch(UserException exception){
			String str="Invalid User Id";
			assertEquals(str, exception.getMessage());
		}
	}
	
	@Test
	void testGetAllUsers(){
		assertNotNull(userService.getAllUsers());
	}
	
//	@Test
//	void testGetUsersByUserType1()throws UserException{
//		assertNotNull(userService.getUsersByUserType("CUSTOMER"));
//	}
//	
//	@Test
//	void testGetUsersByUserType2()throws UserException{
//		assertNotNull(userService.getUsersByUserType("ADMIN"));
//	}
//	
//	@Test
//	void testGetUsersByUserTypeException()throws UserException{
//		assertThrows(UserException.class,()->userService.getUsersByUserType("xxx"));
//	}
//	
//	@Test
//	void testGetUsersByUserTypeExpMsg()throws UserException{
//		try {
//			userService.getUsersByUserType("xxx");
//		}
//		catch(UserException exception) {
//			String str="There are no users of type:"+"xxx";
//			assertEquals(str,exception.getMessage());
//		}
//	}
}

