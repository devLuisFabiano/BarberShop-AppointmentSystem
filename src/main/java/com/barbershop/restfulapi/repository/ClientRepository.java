package com.barbershop.restfulapi.repository;

import com.barbershop.restfulapi.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
