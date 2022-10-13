package com.example.ActiveMQ_JWT.repository;

import com.example.ActiveMQ_JWT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
