package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        User user = userService.findByEmail("admin@gmail.com");
        if (user == null) {
            userService.save(new User("admin", "admin@gmail.com", new BCryptPasswordEncoder().encode("admin123"), Role.ADMIN.name()));
        }
        return "login"; // aponta para templates/login.html
    }
}
