package com.barbershop.restfulapi.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TimeOff {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "barber_id")
    private Barber barber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;
}
