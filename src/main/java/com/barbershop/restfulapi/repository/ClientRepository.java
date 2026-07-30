package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPublicId(UUID id);
    Optional<Client> findByUser(Long id);

    Optional<Client> findByUser_UserId(long l);
}
