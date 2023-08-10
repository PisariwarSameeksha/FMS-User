package com.fmsUser.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmsUser.DTO.UserDTO;
import com.fmsUser.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByEmail(String email);

	List<Users> findByUserType(String userType);

	Users save(UserDTO newUser);	

}