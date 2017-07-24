package com.springbootsecuritytemplate.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootsecuritytemplate.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}