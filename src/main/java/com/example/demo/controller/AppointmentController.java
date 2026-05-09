package com.example.demo.controller;

import com.example.demo.dto.AppointmentRegisterDTO;
import com.example.demo.model.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.BarberRepository;
import com.example.demo.repository.UserRepository;
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
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BarberRepository  barberRepository;
    @Autowired
    private UserRepository userRepository;

    // Available time slots
    private static final List<LocalTime> TIME_SLOTS = List.of(
            LocalTime.of(8, 0),
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0)
    );

    @GetMapping("/appointment")
    public String showForm(Model model, Authentication authentication) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("barbers", barberRepository.findAll());
        model.addAttribute("timeSlots", TIME_SLOTS);
        model.addAttribute("user", authentication.getName());
        model.addAttribute("services", Service.values());
        return "appointments";
    }

    @PostMapping("/appointment")
    public String saveAppointment(Model model, @Valid @ModelAttribute AppointmentRegisterDTO data,
                                  BindingResult bindingResult, Authentication authentication,
                                  @RequestParam String date, @RequestParam String time) {
        Barber barber = barberRepository.findById(data.getBarber().getId()).orElseThrow();
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(date),
                LocalTime.parse(time)
        );

        if (appointmentRepository.existsByBarberAndDateTime(barber, dateTime)) {
            model.addAttribute("appointment", data);
            model.addAttribute("barbers", barberRepository.findAll());
            model.addAttribute("timeSlots", TIME_SLOTS);
            model.addAttribute("services", Service.values());
            model.addAttribute("error", "Este barbeiro já possui um agendamento neste horário.");
            return "appointments";
        }

        if(dateTime.isBefore(LocalDateTime.now())) {
            //bindingResult.addError(new FieldError("appointment", "date", "A data do agendamento nao pode ser no passado"));
            model.addAttribute("appointment", data);
            model.addAttribute("barbers", barberRepository.findAll());
            model.addAttribute("timeSlots", TIME_SLOTS);
            model.addAttribute("services", Service.values());
            model.addAttribute("error", "A data do agendamento nao pode ser no passado.");
            return "appointments";

        }

        if(bindingResult.hasErrors()){
            return "appointments";
        }
        try {
            Appointment appointment = new Appointment(null,
                    barberRepository.findById(data.getBarber().getId()).orElseThrow(),
                    userRepository.findByEmail(authentication.getName()),
                    dateTime,
                    data.getService(),
                    data.getService().getPrice());
            model.addAttribute("success", true);
            appointmentRepository.save(appointment);

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
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();

        // segurança: só cancela se o appointment for do usuário logado
        if (appointment.getUser().getId().equals(user.getId())) {
            appointmentRepository.deleteAppointmentById(id);
        }

        return "redirect:/profile";
    }
}
