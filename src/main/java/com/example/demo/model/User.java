package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
@Entity(name = "client")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    public User(String name, String email, String password , String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
//
//    public void addAppointment(Appointment appointment) {
//        this.appointments.add(appointment);
//    }
//
//    public void removeAppointment(Appointment appointment) {
//        this.appointments.add(appointment);
//    }
}
