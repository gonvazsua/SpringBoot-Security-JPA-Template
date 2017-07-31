package com.springbootsecuritytemplate.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootsecuritytemplate.models.User;

/**
 * @author gonzalo
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
	/**
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
}