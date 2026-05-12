package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    @Test
    void deveCriarAgendamentoComTodosOsCampos() {
        Barber barber = new Barber("Carlos Silva", "carlos@barbearia.com");
        User user = new User();
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 10, 9, 0);

        Appointment appointment = new Appointment(
                1L,
                barber,
                user,
                dateTime,
                Service.CORTE,
                Service.CORTE.getPrice()
        );

        assertNotNull(appointment);
        assertEquals(1L, appointment.getId());
        assertEquals(barber, appointment.getBarber());
        assertEquals(user, appointment.getUser());
        assertEquals(dateTime, appointment.getDateTime());
        assertEquals(Service.CORTE, appointment.getService());
    }

    @Test
    void deveCriarAgendamentoVazioComNoArgsConstructor() {
        Appointment vazio = new Appointment();
        assertNotNull(vazio);
        assertNull(vazio.getId());
        assertNull(vazio.getBarber());
        assertNull(vazio.getService());
    }

    @Test
    void deveRetornarPrecoFormatadoEmReais() {
        Barber barber = new Barber("Carlos Silva", "carlos@barbearia.com");
        User user = new User();
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 10, 9, 0);

        Appointment appointment = new Appointment(
                1L,
                barber,
                user,
                dateTime,
                Service.CORTE,
                Service.CORTE.getPrice()
        );

        Appointment appt = new Appointment(1L, barber, user, dateTime, Service.CORTE, Service.CORTE.getPrice());
        assertEquals("R$ 35,00", appt.getFormattedPrice());
    }
}
