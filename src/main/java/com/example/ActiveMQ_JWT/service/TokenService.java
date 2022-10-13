package com.example.ActiveMQ_JWT.service;

import com.example.ActiveMQ_JWT.entity.Token;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}
