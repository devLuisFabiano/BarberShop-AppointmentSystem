package com.barbershop.restfulapi.dto;

import com.barbershop.restfulapi.model.enums.AppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreateResponse(
        UUID publicID,
        String barber,
        LocalDateTime dateTime,
        @Enumerated(EnumType.STRING)
        AppointmentStatus status,
        String service
) {
}
