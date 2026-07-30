package com.barbershop.restfulapi.controller;

import com.barbershop.restfulapi.config.EmailAlreadyExistsException;
import com.barbershop.restfulapi.dto.BarberCreateRequest;
import com.barbershop.restfulapi.dto.BarberCreateResponse;
import com.barbershop.restfulapi.dto.UserRegisterRequest;
import com.barbershop.restfulapi.dto.UserRegisterResponse;
import com.barbershop.restfulapi.model.entities.Barber;
import com.barbershop.restfulapi.model.entities.User;
import com.barbershop.restfulapi.model.enums.Role;
import com.barbershop.restfulapi.repository.BarberRepository;
import com.barbershop.restfulapi.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class BarberController {

    private final UserRepository userRepository;
    private final BarberRepository barberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/barbers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BarberCreateResponse> register (@RequestBody @Valid BarberCreateRequest dto) {
        Optional<User> userDb = userRepository.findByEmail(dto.email());

        if(userDb.isPresent()){
            throw new EmailAlreadyExistsException(dto.email());
        }

        User user = new User(null, UUID.randomUUID() ,dto.email(), passwordEncoder.encode(dto.password()), Role.BARBER, null, LocalDateTime.now());
        Barber barber = new Barber(null, UUID.randomUUID(), user, dto.name(), null);
        userRepository.save(user);
        barberRepository.save(barber);

        BarberCreateResponse response = new BarberCreateResponse(barber.getPublicId(), barber.getName(), barber.getUser().getEmail(), barber.getUser().getRole());

        return ResponseEntity.created(URI.create("/barbers/" + barber.getPublicId())).body(response);

    }
}
