package com.barbershop.restfulapi.controller;


import com.barbershop.restfulapi.dto.BarberCreateResponse;
import com.barbershop.restfulapi.dto.ServiceCreateRequest;
import com.barbershop.restfulapi.dto.ServiceCreateResponse;
import com.barbershop.restfulapi.model.entities.Service;
import com.barbershop.restfulapi.repository.ServiceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceRepository serviceRepository;

    @PostMapping("/services")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ServiceCreateResponse> createService (@RequestBody @Valid ServiceCreateRequest dto) {
        Optional<Service> serviceDB = serviceRepository.findByName(dto.name());

        if(serviceDB.isPresent()){
            throw new RuntimeException();
        }

        Service service = new Service(null, UUID.randomUUID(), dto.name(), dto.price(), true);
        serviceRepository.save(service);
        ServiceCreateResponse response = new ServiceCreateResponse(service.getPublicId(), service.getName(), service.getPrice(), service.isActive());

        return ResponseEntity.created(URI.create("/services/" + service.getPublicId())).body(response);
    }

    @GetMapping("/services")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_CLIENT')")
    public ResponseEntity<List<ServiceCreateResponse>> getAll(){
        List<ServiceCreateResponse> list = serviceRepository.findAll().stream()
                .map(service-> new ServiceCreateResponse(service.getPublicId(), service.getName(), service.getPrice(), service.isActive()))
                .toList();
        return ResponseEntity.ok().body(list);
    }
}
