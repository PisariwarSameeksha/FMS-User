package com.fmsUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fmsUser.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select user from Users user where userName = :userName and password=:password")
	public User getUserByUsernameAndPassword(@Param("userName") String userName, @Param("password") String password);

	public User findByUserName(String username);
}
