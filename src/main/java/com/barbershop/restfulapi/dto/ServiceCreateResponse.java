package com.barbershop.restfulapi.dto;

import java.util.UUID;

public record ServiceCreateResponse(
        UUID publicId,
        String name,
        double price,
        boolean active
) {
}
