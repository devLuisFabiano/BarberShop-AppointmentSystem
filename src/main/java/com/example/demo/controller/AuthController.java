package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "login"; // aponta para templates/login.html
    }
}
