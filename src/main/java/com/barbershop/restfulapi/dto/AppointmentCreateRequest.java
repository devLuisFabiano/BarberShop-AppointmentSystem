package com.barbershop.restfulapi.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreateRequest(
        @NotNull(message = "Barber is required")
        UUID barberId,
        @NotNull(message = "Service is required")
        UUID serviceId,
        @Future(message = "Appointment must be scheduled in the future")
        LocalDateTime dateTime
) {
}
