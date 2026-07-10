package com.barbershop.restfulapi.controller;

import com.barbershop.restfulapi.dto.UserRegisterRequest;
import com.barbershop.restfulapi.dto.UserRegisterResponse;
import com.barbershop.restfulapi.model.entities.User;
import com.barbershop.restfulapi.repository.BarberRepository;
import com.barbershop.restfulapi.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BarberController {

    private final UserRepository userRepository;
    private final BarberRepository barberRepository;

    @PostMapping("/barbers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BarberCreateResponse> register (@RequestBody @Valid BarberCreateRequest dto) {
        Optional<User> userDb = userRepository.findByEmail(dto.email());

    }
}
