package com.springbootsecuritytemplate.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.springbootsecuritytemplate.models.User;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);

}
