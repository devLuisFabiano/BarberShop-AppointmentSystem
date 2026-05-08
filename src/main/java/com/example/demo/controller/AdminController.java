package com.example.demo.controller;

import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.BarberRepository;
import com.example.demo.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import com.example.demo.model.Barber;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
//    @Autowired
//    private BarberService barberService;
    @Autowired
    private BarberRepository barberRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Barber> employees = barberRepository.findAll();
        if(employees.isEmpty()){
            model.addAttribute("msg", "Nenhum Barbeiro registrado");
            return "dashboard";
        }
        model.addAttribute("employees", employees);

        return "dashboard";
    }

    @PostMapping("/admin/dashboard")
    public String register(Model model, @Valid @ModelAttribute EmployeeRegisterDTO employeeRegisterDTO,
                           BindingResult bindingResult) {

        Barber barberExists = barberRepository.findByName(employeeRegisterDTO.getName());
        if(barberExists != null){
            bindingResult.addError(new FieldError("employees", "name", "this employee already exists"));
        }

        if(bindingResult.hasErrors()){
            return "dashboard";
        }
        try {
            Barber barber = new Barber(employeeRegisterDTO.getName(), employeeRegisterDTO.getEmail());
            barberRepository.save(barber);
            model.addAttribute("success", true);
            model.addAttribute("employees", barberRepository.findAll());
        }
        catch (Exception e){
            System.out.printf("Error: %s%n", e.getMessage());
            return "dashboard";
        }

        return "dashboard";

    }

    @PostMapping("/admin/employees/delete/{id}")
    public String removeEmployee(@PathVariable Long id, Model model) {

        Optional<Barber> employees = barberRepository.findById(id);

        if(employees.isPresent()){
            barberRepository.deleteById(id);
            model.addAttribute("employees", barberRepository.findAll());
        }

        return "redirect:/admin/dashboard";
    }
}
