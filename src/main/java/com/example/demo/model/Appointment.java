package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "appointment")
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Barber barber;
    @ManyToOne
    private User user;
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private Service service;
    private Double price;

    public String getFormattedPrice() {
        return String.format("R$ %.2f", price).replace(".", ",");
    }
}
