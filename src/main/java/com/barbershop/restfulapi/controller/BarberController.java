package com.barbershop.restfulapi.controller;

import com.barbershop.restfulapi.config.EmailAlreadyExistsException;
import com.barbershop.restfulapi.dto.*;
import com.barbershop.restfulapi.model.entities.Barber;
import com.barbershop.restfulapi.model.entities.User;
import com.barbershop.restfulapi.model.enums.Role;
import com.barbershop.restfulapi.repository.BarberRepository;
import com.barbershop.restfulapi.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/barbers")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_CLIENT')")
    public ResponseEntity<List<BarberCreateResponse>> getAllBarbers(){
        List<BarberCreateResponse> list = barberRepository.findAll().stream()
                .map(barber -> new BarberCreateResponse(barber.getPublicId(), barber.getName(), barber.getUser().getEmail(), barber.getUser().getRole()))
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/barbers/{publicId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_BARBER', 'SCOPE_ADMIN')")
    public ResponseEntity<BarberCreateResponse> updateBarber(
            @PathVariable UUID publicId,
            @Valid @RequestBody BarberUpdateRequest dto,
            @AuthenticationPrincipal Jwt jwt) {

        Barber barber = barberRepository.findByPublicId(publicId)
                .orElseThrow(() -> new EmailAlreadyExistsException("a"));

        boolean isAdmin = jwt.getClaimAsStringList("authorities").contains("SCOPE_ADMIN");
        boolean isOwner = barber.getUser().getUserId().equals(Long.parseLong(jwt.getSubject()));

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You can only edit your own barber profile");
        }

        barber.setName(dto.name());

        barberRepository.save(barber);

        BarberCreateResponse response = new BarberCreateResponse(
                barber.getPublicId(),
                barber.getName(),
                barber.getUser().getEmail(),
                barber.getUser().getRole()
        );

        return ResponseEntity.ok(response);
    }

}
