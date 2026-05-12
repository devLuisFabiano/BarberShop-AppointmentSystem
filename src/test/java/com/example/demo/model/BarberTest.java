package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BarberTest {
    @Test
    void deveCriarBarbeiroComNomeEEmail() {
        Barber barber = new Barber("Carlos Silva", "carlos@barbearia.com");
        Appointment appointment = new Appointment(
                1L,
                barber,
                new User(),
                LocalDateTime.of(2025, 6, 10, 9, 0),
                Service.CORTE,
                Service.CORTE.getPrice()
        );

        assertEquals("Carlos Silva", barber.getName());
        assertEquals("carlos@barbearia.com", barber.getEmail());
    }

    @Test
    void deveCriarBarbeiroVazio() {
        Barber vazio = new Barber();

        assertNotNull(vazio);
        assertNull(vazio.getId());
        assertNull(vazio.getEmail());
        assertNull(vazio.getName());
    }

    @Test
    void deveAlterarBarbeiro() {
        Barber barber = new Barber("Carlos Silva", "carlos@barbearia.com");
        Appointment appointment = new Appointment(
                1L,
                barber,
                new User(),
                LocalDateTime.of(2025, 6, 10, 9, 0),
                Service.CORTE,
                Service.CORTE.getPrice()
        );

        barber.setEmail("l@gmai.com");
        barber.setName("Luis");

        assertEquals("Luis", barber.getName());
        assertEquals("l@gmai.com", barber.getEmail());
    }
}
