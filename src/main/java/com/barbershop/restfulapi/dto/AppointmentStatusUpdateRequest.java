package com.barbershop.restfulapi.dto;

import jakarta.validation.constraints.NotBlank;

public record AppointmentStatusUpdateRequest(
        @NotBlank(message = "status is required")
        String status
) {
}
