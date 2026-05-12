package com.example.demo.controller;

import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.model.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProfileController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        //List<Appointment> appointments = appointmentRepository.findByUser(user);
        model.addAttribute("username", user.getName());
        model.addAttribute("appointments", appointmentRepository.findByUser(user));

        return "profile";
    }

    @GetMapping("/profile/edit")
    public String ShowEditProfile(Model model, Authentication authentication) {
        User  user = userRepository.findByEmail(authentication.getName());
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("UserUpdateDTO", userUpdateDTO);
        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute("UserUpdateDTO") UserUpdateDTO userUpdateDTO, BindingResult bindingResult) {
        try{
            User user = userRepository.findByEmail(userUpdateDTO.getEmail());

            if (bindingResult.hasErrors()) {
                return "editProfile";
            }
            user.setName(userUpdateDTO.getName());
            userRepository.save(user);
            return "redirect:/profile";
        }catch (Exception e){
            return "editProfile";
        }
    }
}
