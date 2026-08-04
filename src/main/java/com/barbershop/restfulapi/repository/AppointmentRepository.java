package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Appointment;
import com.barbershop.restfulapi.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDateTime(LocalDateTime dateTime);
    Optional<Appointment> findByPublicId(UUID id);
    List<Appointment> findByClient(Client client);
}
