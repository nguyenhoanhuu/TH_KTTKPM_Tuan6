package com.example.ActiveMQ_JWT.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class MessageRestController {

    @PostMapping("/send")
    public String sendMessage(@PathVariable String mess) {
        return mess;
    }
}
