package com.barbershop.restfulapi.controller;

import com.barbershop.restfulapi.config.EmailAlreadyExistsException;
import com.barbershop.restfulapi.config.PasswordMismatchException;
import com.barbershop.restfulapi.dto.UserRegisterRequest;
import com.barbershop.restfulapi.dto.UserRegisterResponse;
import com.barbershop.restfulapi.model.entities.Client;
import com.barbershop.restfulapi.model.entities.User;
import com.barbershop.restfulapi.model.enums.Role;
import com.barbershop.restfulapi.repository.ClientRepository;
import com.barbershop.restfulapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register (@RequestBody @Valid UserRegisterRequest dto) {
        Optional<User> userDb = userRepository.findByEmail(dto.email());

        if(userDb.isPresent()){
            throw new EmailAlreadyExistsException(dto.email());
        }

        User user = new User(null, UUID.randomUUID() ,dto.email(), passwordEncoder.encode(dto.password()), Role.ADMIN, null, LocalDateTime.now());
        Client client = new Client(null, UUID.randomUUID(), user, dto.name(), null, null, LocalDateTime.now(), LocalDateTime.now());
        UserRegisterResponse response = new UserRegisterResponse(user.getPublicId(), dto.name(), user.getEmail(), user.getRole());
        userRepository.save(user);
        clientRepository.save(client);

        return ResponseEntity.created(URI.create("/users/" + user.getPublicId())).body(response);
    }
}
