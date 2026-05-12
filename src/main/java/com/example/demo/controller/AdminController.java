package com.example.demo.controller;

import com.example.demo.dto.EmployeeRegisterDTO;
import com.example.demo.model.User;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.example.demo.model.Barber;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BarberService barberService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<Barber> employees = barberService.getAllBarbers();
        if(employees.isEmpty()){
            model.addAttribute("msg", "Nenhum Barbeiro registrado");
            return "dashboard";
        }
        model.addAttribute("employees", employees);
        model.addAttribute("err", new EmployeeRegisterDTO());

        return "dashboard";
    }

    @PostMapping("/admin/dashboard")
    public String register(Model model, @Valid @ModelAttribute("err") EmployeeRegisterDTO employeeRegisterDTO, BindingResult bindingResult) {

        barberService.checkIfBarberAlreadyExists(employeeRegisterDTO.getEmail(), bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("employees", barberService.getAllBarbers());
            return "dashboard";
        }
        try {
            barberService.saveBarber(new Barber(employeeRegisterDTO.getName(), employeeRegisterDTO.getEmail()), model);
        }
        catch (Exception e){
            System.out.printf("Error: %s%n", e.getMessage());
            return "dashboard";
        }

        return "dashboard";

    }

    @PostMapping("/admin/employees/delete/{id}")
    public String removeEmployee(@PathVariable Long id, Model model) {

        barberService.deleteBarber(id, model);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("admin/employees/{id}")
    public String detailsEmployee(@PathVariable Long id, Model model, Authentication authentication) {
        Barber barber = barberService.getBarberById(id);
        model.addAttribute("username", barber.getName());
        model.addAttribute("appointments", appointmentService.getAppointmentByBarber(barber));

        return "barber-profile";
    }
}
