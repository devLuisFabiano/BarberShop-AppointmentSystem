package com.barbershop.restfulapi.dto;

import com.barbershop.restfulapi.model.enums.Role;

import java.util.UUID;

public record UserRegisterResponse(UUID publicId, String name, String email, Role role) {
}

