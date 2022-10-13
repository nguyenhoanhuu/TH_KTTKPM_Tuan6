package com.example.ActiveMQ_JWT.service;

import com.example.ActiveMQ_JWT.authen.UserPrincipal;
import com.example.ActiveMQ_JWT.entity.User;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
    User saveUser(User user);
    User getUserByUsername(String username);
}
