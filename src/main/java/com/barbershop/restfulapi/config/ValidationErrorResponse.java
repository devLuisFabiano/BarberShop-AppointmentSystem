package com.barbershop.restfulapi.config;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String path,
        Map<String, String> fieldErrors
) {
}
