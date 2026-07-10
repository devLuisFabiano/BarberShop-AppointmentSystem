package com.barbershop.restfulapi.model.entities;

import com.barbershop.restfulapi.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", nullable = false)
    private Long id;
    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "service_id", nullable = false)
    private Service service;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime; // derived from startTime + service.durationMinutes, but stored for fast querying
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULE;
    @Column(updatable = false) @CreationTimestamp
    private Instant createdAt;
    private LocalDateTime updatedAt;

    public Appointment(Client client, Barber barber, Service service, LocalDate date, LocalTime startTime) {
        this.client = client;
        this.barber = barber;
        this.service = service;
        this.date = date;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(service.getDurationMinutes());
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setService(Service service) {
        this.service = service;
        if (this.startTime != null) {
            this.endTime = this.startTime.plusMinutes(service.getDurationMinutes());
        }
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        if (this.service != null) {
            this.endTime = startTime.plusMinutes(this.service.getDurationMinutes());
        }
    }
}