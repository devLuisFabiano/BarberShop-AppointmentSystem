package com.example.demo.dto;

import com.example.demo.model.Barber;
import com.example.demo.model.Service;
import com.example.demo.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentRegisterDTO {
    private User user;
    private Barber barber;
    @Enumerated(EnumType.STRING)
    private Service service;
    private  Double price;
}
