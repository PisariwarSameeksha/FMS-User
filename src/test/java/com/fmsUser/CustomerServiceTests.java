package com.fmsUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fmsUser.controller.CustomerController;
import com.fmsUser.entity.Customer;
import com.fmsUser.exception.CustomerException;
import com.fmsUser.repository.CustomerRepository;
import com.fmsUser.repository.UserRepository;
import com.fmsUser.service.CustomerServiceImplementation;

import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerServiceImplementation customerService;
    
    @Mock
    private CustomerController customerController;
    
    @Test
    void testAddUser_ValidUser() throws CustomerException {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer newUser = new Customer("newuser", "newuser@example.com", "password", "New User", dob, "9876543210");

        when(userRepository.findByUserName(newUser.getUserName())).thenReturn(null);
        when(customerRepository.save(newUser)).thenReturn(newUser);

        Customer addedUser = customerService.addUser(newUser);

        assertNotNull(addedUser);
        assertEquals(newUser.getUserName(), addedUser.getUserName());
        assertEquals(newUser.getEmail(), addedUser.getEmail());
        assertEquals(newUser.getPassword(), addedUser.getPassword());
        assertEquals(newUser.getName(), addedUser.getName());
        assertEquals(newUser.getDob(), addedUser.getDob());
        assertEquals(newUser.getContactNo(), addedUser.getContactNo());
        assertEquals(newUser.getAddress(), addedUser.getAddress());

        verify(userRepository).findByUserName(newUser.getUserName());
        verify(customerRepository).save(newUser);
    }

    @Test
    void testAddUser_UserAlreadyExists() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob, "9876543210");

        when(userRepository.findByUserName(existingUser.getUserName())).thenReturn(existingUser);

        assertThrows(CustomerException.class, () -> customerService.addUser(existingUser));

        verify(userRepository).findByUserName(existingUser.getUserName());
        verify(customerRepository, never()).save(existingUser);
    }

    @Test
    void testAddUser_EmailNormalization() throws CustomerException {
    	LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer newUser = new Customer("newuser", "NewUser@Example.com", "password", "New User", dob, "8437672865");

        when(customerRepository.findByUserName(newUser.getUserName())).thenReturn(null);
        when(customerRepository.save(newUser)).thenReturn(newUser);

        Customer result = customerService.addUser(newUser);

        assertNotNull(result);
        assertEquals("newuser@example.com", result.getEmail());
        verify(customerRepository, times(1)).save(newUser);
    }
    
    @Test
    void testGetUserById_ValidUserId() throws CustomerException {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob, "9876543210");
        
        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        Customer retrievedUser = customerService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(existingUser, retrievedUser);

        verify(customerRepository).findById(userId);
    }

    @Test
    void testGetUserById_InvalidUserId() {
        Long userId = 2L;

        when(customerRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.getUserById(userId));

        verify(customerRepository).findById(userId);
    }
    
    @Test
    void testUpdateUser_Success() throws CustomerException {
        Long userId = 1L;
        Customer existingUser = new Customer("Existing", "existing@example.com", "password", "Existing User", LocalDate.now(), "9876543210");
        Customer updateUser = new Customer("Updated", "updated@example.com", "newpassword", "Updated User", LocalDate.now(), "8765432109");

        // Set up mock behavior for findById
        when(customerRepository.existsById(userId)).thenReturn(true);
        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Set up mock behavior for findByEmail
        when(customerRepository.findByEmail(updateUser.getEmail())).thenReturn(Optional.empty());

        // Set up mock behavior for save
        when(customerRepository.save(any(Customer.class))).thenReturn(updateUser);

        // Call the method
        Customer result = customerService.updateUser(userId, updateUser);

        // Assert the result and verify interactions
        assertNotNull(result);
        assertEquals(updateUser.getName(), result.getName());
        assertEquals(updateUser.getEmail(), result.getEmail());
        assertEquals(updateUser.getPassword(), result.getPassword());
        assertEquals(updateUser.getContactNo(), result.getContactNo());
        assertEquals(updateUser.getDob(), result.getDob());
        assertEquals(updateUser.getAddress(), result.getAddress());

        verify(customerRepository).existsById(userId);
        verify(customerRepository).findById(userId);
        verify(customerRepository).findByEmail(updateUser.getEmail());
    }

    
    @Test
    void testUpdateUser_EmailAlreadyExists() {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);

        // Existing user with the same email as the one being updated
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob, "9876543210");
        
        // Updated user with the same email as existingUser
        Customer updateUser = new Customer("updated", "existing@example.com", "newpassword", "Updated User", dob, "8764238762");

     // Mock the repository behavior
        when(customerRepository.existsById(userId)).thenReturn(true);
        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Mock findByEmail to return the user being updated, not another existing user
        when(customerRepository.findByEmail(updateUser.getEmail())).thenReturn(Optional.of(updateUser));

   
    }


    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 1L;
        Customer updateUser = new Customer("Updated", "updated@example.com", "newpassword", "Updated User", LocalDate.now(), "8765432109");

        when(customerRepository.existsById(userId)).thenReturn(false);

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.updateUser(userId, updateUser));
        assertEquals("Cannot Update User Details As User Id not found", exception.getMessage());

        verify(customerRepository).existsById(userId);
        verify(customerRepository, never()).findById(userId);
        verify(customerRepository, never()).findByEmail(updateUser.getEmail());
        verify(customerRepository, never()).save(any(Customer.class));
    }
            
    @Test
    void testDeleteUserById_Success() throws CustomerException {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer user = new Customer("test", "test@example.com", "password", "Test User", dob, "8992734794");
        user.setUserId(userId);

        when(customerRepository.findById(userId)).thenReturn(Optional.of(user));

        Customer result = customerService.deleteUserById(userId);

        assertNotNull(result);
        assertEquals(user, result);
        verify(customerRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        Long userId = 1L;

        when(customerRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.deleteUserById(userId));
        verify(customerRepository, never()).deleteById(userId);
    }
    
    @Test
    void testGetAllUsers_Success() {
        List<Customer> users = new ArrayList<>();
        LocalDate dob = LocalDate.of(1990, 1, 1);
        users.add(new Customer("user1", "user1@example.com", "password1", "User 1", dob,"6287672378"));
        users.add(new Customer("user2", "user2@example.com", "password2", "User 2", dob,"9876543210"));

        when(customerRepository.findAll()).thenReturn(users);

        List<Customer> result = customerService.getAllUsers();

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_EmptyList() {
        List<Customer> users = new ArrayList<>();

        when(customerRepository.findAll()).thenReturn(users);

        List<Customer> result = customerService.getAllUsers();

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
        verify(customerRepository, times(1)).findAll();
    }
    
    @Test
    void testChangePassword_Success() throws CustomerException {
        String email = "user@example.com";
        String newPassword = "newPassword";
        
        Customer existingCustomer = new Customer("userName", email, "oldPassword", "User Name", LocalDate.of(1990, 1, 1), "1234567890");

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(existingCustomer));

        Customer result = customerService.changePassword(email, newPassword);

        assertEquals(newPassword, result.getPassword());
        verify(customerRepository).save(existingCustomer);
    }
    
    @Test
    void testChangePassword_UserNotFound() {
        String email = "nonexistent@example.com";
        String newPassword = "newPassword";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.changePassword(email, newPassword));
        verify(customerRepository, never()).save(any(Customer.class));
    }
    

    
    //repository test cases    
    @Test
    void testFindByEmail_ExistingEmail() {
        String email = "existing@example.com";
        Customer existingUser = new Customer("Existing User", email, "password", "Existing User", LocalDate.now(), "9876543210");
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        Optional<Customer> result = customerRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(existingUser.getEmail(), result.get().getEmail());
        verify(customerRepository).findByEmail(email);
    }
    
    @Test
    void testFindByEmail_NonExistingEmail() {
        String email = "nonexisting@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Customer> result = customerRepository.findByEmail(email);

        assertFalse(result.isPresent());
        verify(customerRepository).findByEmail(email);
    }
    
    @Test
    void testFindByUserName_ExistingUserName() {
        String userName = "existingUser";
        Customer existingUser = new Customer(userName, "existing@example.com", "password", "Existing User", LocalDate.now(), "9876543210");
        when(customerRepository.findByUserName(userName)).thenReturn(existingUser);

        Object result = customerRepository.findByUserName(userName);

        assertNotNull(result);
        assertTrue(result instanceof Customer);
        assertEquals(existingUser.getUserName(), ((Customer) result).getUserName());
        verify(customerRepository).findByUserName(userName);
    }
    
    @Test
    void testFindByUserName_NonExistingUserName() {
        String userName = "nonexistingUser";
        when(customerRepository.findByUserName(userName)).thenReturn(null);

        Object result = customerRepository.findByUserName(userName);

        assertNull(result);
        verify(customerRepository).findByUserName(userName);
    }
    
    


}

