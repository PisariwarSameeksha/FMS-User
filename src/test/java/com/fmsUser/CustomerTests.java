package com.fmsUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fmsUser.entity.Customer;
import com.fmsUser.exception.CustomerException;
import com.fmsUser.repository.CustomerRepository;
import com.fmsUser.service.CustomerServiceImplementation;

import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerTests {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImplementation customerService;

    @Test
    void testAddUser_Success() throws CustomerException {
    	LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer newUser = new Customer("newuser", "newuser@example.com", "password", "New User", dob, "9848767231");

        when(customerRepository.findByUserName(newUser.getUserName())).thenReturn(null);
        when(customerRepository.save(newUser)).thenReturn(newUser);

        Customer result = customerService.addUser(newUser);

        assertNotNull(result);
        assertEquals(newUser, result);
        verify(customerRepository, times(1)).save(newUser);
    }

    @Test
    void testAddUser_UserAlreadyExists() {
    	LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob,"9876543210");

        when(customerRepository.findByUserName(existingUser.getUserName())).thenReturn(existingUser);

        assertThrows(CustomerException.class, () -> customerService.addUser(existingUser));
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
    void testUpdateUser_Success() throws CustomerException {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob, "9876543210");
        Customer updatedUser = new Customer("updated", "updated@example.com", "newpassword", "Updated User", dob, "9872376737");
        updatedUser.setUserId(userId);

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(customerRepository.findByEmail(updatedUser.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(updatedUser)).thenReturn(updatedUser);

        Customer result = customerService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser, result);
        verify(customerRepository, times(1)).save(updatedUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer updateUser = new Customer("updated", "updated@example.com", "newpassword", "Updated User", dob, "7625346673");

        when(customerRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.updateUser(userId, updateUser));
        verify(customerRepository, never()).save(updateUser);
    }

    @Test
    void testUpdateUser_EmailAlreadyExists() {
        Long userId = 1L;
        LocalDate dob = LocalDate.of(1990, 1, 1);
        Customer existingUser = new Customer("existing", "existing@example.com", "password", "Existing User", dob, "9876543210");
        Customer updateUser = new Customer("updated", "existing@example.com", "newpassword", "Updated User", dob, "8764238762");

        when(customerRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(customerRepository.findByEmail(updateUser.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(CustomerException.class, () -> customerService.updateUser(userId, updateUser));
        verify(customerRepository, never()).save(updateUser);
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

}

