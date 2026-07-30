package com.barbershop.restfulapi.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceCreateRequest(
        @NotBlank
        String name,
        @NotNull
        @Positive
        double price
) {
}
