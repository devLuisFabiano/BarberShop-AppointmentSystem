package com.example.demo.controller;

import com.example.demo.dto.AppointmentRegisterDTO;
import com.example.demo.model.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.BarberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BarberService barberService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/appointment")
    public String showForm(Model model, Authentication authentication) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("barbers", barberService.getAllBarbers());
        model.addAttribute("timeSlots", appointmentService.getTimeSlots());
        model.addAttribute("user", authentication.getName());
        model.addAttribute("services", Service.values());
        return "appointments";
    }

    @PostMapping("/appointment")
    public String saveAppointment(Model model, @Valid @ModelAttribute AppointmentRegisterDTO data,
                                  BindingResult bindingResult, Authentication authentication,
                                  @RequestParam String date, @RequestParam String time) {
        Barber barber = barberService.getBarberById(data.getBarber().getId());
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));

        if (appointmentService.appointmentExists(barber, dateTime)) {
            model.addAttribute("appointment", data);
            model.addAttribute("barbers", barberService.getAllBarbers());
            model.addAttribute("timeSlots", appointmentService.getTimeSlots());
            model.addAttribute("services", Service.values());
            model.addAttribute("error", "Este barbeiro já possui um agendamento neste horário.");
            return "appointments";
        }

        if(dateTime.isBefore(LocalDateTime.now())) {
            model.addAttribute("appointment", data);
            model.addAttribute("barbers", barberService.getAllBarbers());
            model.addAttribute("timeSlots", appointmentService.getTimeSlots());
            model.addAttribute("services", Service.values());
            model.addAttribute("error", "A data do agendamento nao pode ser no passado.");
            return "appointments";

        }

        if(bindingResult.hasErrors()){
            return "appointments";
        }
        try {
            Appointment appointment = new Appointment(null,
                    barberService.getBarberById(data.getBarber().getId()),
                    userRepository.findByEmail(authentication.getName()),
                    dateTime,
                    data.getService(),
                    data.getService().getPrice());
            model.addAttribute("success", true);
            appointmentService.saveAppointment(appointment);

        }
        catch (Exception e){
            System.out.printf("Error: %s%n", e.getMessage());
            return "appointments";
        }

        return "redirect:/profile";
    }

    @Transactional
    @PostMapping("/appointment/cancel/{id}")
    public String cancel(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        Appointment appointment = appointmentService.getAppointmentById(id);

        if (appointment.getUser().getId().equals(user.getId())) {
            appointmentService.deleteAppointmentById(id);
        }

        return "redirect:/profile";
    }
}
