package com.example.ActiveMQ_JWT.repository;

import com.example.ActiveMQ_JWT.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository
        extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
