package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BarberRepository extends JpaRepository<Barber, Long> {

    Optional<Barber> findByPublicId(UUID id);
}
