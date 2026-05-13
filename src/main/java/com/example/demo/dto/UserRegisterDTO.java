package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    //@Size(min = 12, max = 20, message = "Senha precisa ter entre 12 e 20 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[0-9]).{12,}$", message = "A senha deve ter uma letra maiúscula, um numero e um símbolo")
    private String password;
    @NotBlank
    private String confirmPassword;
}
