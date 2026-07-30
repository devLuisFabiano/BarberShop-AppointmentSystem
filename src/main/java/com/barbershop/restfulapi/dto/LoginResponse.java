package com.barbershop.restfulapi.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
