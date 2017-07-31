package com.springbootsecuritytemplate.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.springbootsecuritytemplate.models.User;

/**
 * @author gonzalo
 *
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {
	
	/**
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

}
