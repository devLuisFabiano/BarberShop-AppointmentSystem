package com.example.demo.controller;

import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/register")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String ShowRegisterForm(Model model){
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        model.addAttribute("success", false);
        return "user-register";
    }

    @PostMapping
    public String register(Model model, @Valid @ModelAttribute UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult){

        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){
            bindingResult.addError(new FieldError("userRegisterDTO", "password", "As senhas não coincidem"));
        }

        User userExists = userRepository.findByEmail(userRegisterDTO.getEmail());
        if(userExists != null){
            bindingResult.addError(new FieldError("userRegisterDTO", "email", "Email já cadastrado"));
        }

        if(bindingResult.hasErrors()){
            return "user-register";
        }
        try {
            User user = new User(userRegisterDTO.getName(),
                    userRegisterDTO.getEmail(),
                    new BCryptPasswordEncoder().encode(userRegisterDTO.getPassword()),
                    Role.USER.name());
            userRepository.save(user);
            model.addAttribute("success", true);
        }
        catch (Exception e){
            System.out.printf("Error: %s%n", e.getMessage());
            return "user-register";
        }

        return "user-register";
    }

}
