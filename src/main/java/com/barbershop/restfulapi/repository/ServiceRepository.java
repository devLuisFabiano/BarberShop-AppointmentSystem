package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findByName(String name);
    Optional<Service> findByPublicId(UUID id);
}
