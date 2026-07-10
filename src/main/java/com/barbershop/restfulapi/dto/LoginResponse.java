package com.barbershop.restfulapi.dto;

public record LoginResponse(String acessToken, Long expiresIn) {
}
