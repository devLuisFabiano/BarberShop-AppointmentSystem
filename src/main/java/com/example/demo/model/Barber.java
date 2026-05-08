package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Barber{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    public Barber(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void  addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public  void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
}
