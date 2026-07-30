package com.barbershop.restfulapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "tb_barbers")
@Entity
public class Barber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barber_id", nullable = false)
    private Long barberId;
    @Column(name = "public_id", nullable = false)
    private UUID publicId = UUID.randomUUID();
    @OneToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String name;
    @OneToMany(mappedBy = "barber")
    private List<Appointment> appointments = new ArrayList<>();
}
