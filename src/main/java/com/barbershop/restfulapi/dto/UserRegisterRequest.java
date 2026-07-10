package com.barbershop.restfulapi.dto;

import com.barbershop.restfulapi.config.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@PasswordMatch
public record UserRegisterRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                message = "Password must be at least 12 characters, contain an uppercase letter and a symbol")
        String password,
        @NotBlank(message = "Confirm password is required")
        String confirmPassword) {

}
