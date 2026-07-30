package com.barbershop.restfulapi.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tb_services")
public class Service {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Long id;
    @Column(name = "public_id", nullable = false)
    private UUID publicId;
    private String name;
    private double price;
    private boolean active;
}
