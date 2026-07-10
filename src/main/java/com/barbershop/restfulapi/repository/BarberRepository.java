package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}
