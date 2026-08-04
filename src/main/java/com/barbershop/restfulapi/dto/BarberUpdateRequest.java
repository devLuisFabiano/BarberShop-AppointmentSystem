package com.barbershop.restfulapi.dto;

import jakarta.validation.constraints.NotBlank;

public record BarberUpdateRequest(
        @NotBlank(message = "name is required")
        String name
) {
}
