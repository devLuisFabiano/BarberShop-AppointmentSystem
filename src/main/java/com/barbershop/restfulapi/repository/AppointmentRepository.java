package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDateTime(LocalDateTime dateTime);
    Optional<Appointment> findByPublicId(UUID id);
}
