package com.barbershop.restfulapi.config;

import com.barbershop.restfulapi.dto.UserRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegisterRequest> {

    @Override
    public boolean isValid(UserRegisterRequest dto, ConstraintValidatorContext context) {
        if (dto.password() == null || dto.confirmPassword() == null) return true; // @NotBlank handles nulls separately

        boolean matches = dto.password().equals(dto.confirmPassword());

        if (!matches) {
            // Attach the error to the confirmPassword field specifically, not the whole object
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return matches;
    }
}