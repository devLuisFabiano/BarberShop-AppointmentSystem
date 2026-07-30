package com.barbershop.restfulapi.controller;

import com.barbershop.restfulapi.config.EmailAlreadyExistsException;
import com.barbershop.restfulapi.dto.*;
import com.barbershop.restfulapi.model.entities.*;
import com.barbershop.restfulapi.model.enums.AppointmentStatus;
import com.barbershop.restfulapi.repository.*;
import com.barbershop.restfulapi.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final BarberRepository barberRepository;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    @PreAuthorize("hasAuthority('SCOPE_CLIENT')")
    public ResponseEntity<AppointmentCreateResponse> createAppointment (@RequestBody @Valid AppointmentCreateRequest dto,
                                                                        @AuthenticationPrincipal Jwt jwt) {

        Optional<Appointment> appointmentBD = appointmentRepository.findByDateTime(dto.dateTime());

        if (appointmentBD.isPresent()) {
            throw new  EmailAlreadyExistsException("a");
        }

        Optional<Client> client = clientRepository.findByUser_UserId(Long.parseLong(jwt.getSubject()));
        Optional<Barber> barber = barberRepository.findByPublicId(dto.barberId());
        Optional<Service> service = serviceRepository.findByPublicId(dto.serviceId());
        Appointment appointment = new Appointment(null, UUID.randomUUID(), client.get(), barber.get(), service.get(), dto.dateTime(), AppointmentStatus.SCHEDULED, null, LocalDateTime.now());
        appointmentRepository.save(appointment);
        AppointmentCreateResponse response = new AppointmentCreateResponse(appointment.getPublicId(), appointment.getBarber().getName(), appointment.getDateTime(), appointment.getStatus(), appointment.getService().getName());

        return ResponseEntity.created(URI.create("/appointments/" + appointment.getPublicId())).body(response);
    }

    @PatchMapping("/appointments/{publicId}/status")
    @PreAuthorize("hasAuthority('SCOPE_CLIENT')")
    public ResponseEntity<AppointmentCreateResponse> updateStatus (@PathVariable UUID publicId,
                                                                   @Valid @RequestBody AppointmentStatusUpdateRequest dto,
                                                                   @AuthenticationPrincipal Jwt jwt) {

        Optional<Appointment> appointmentDb = appointmentRepository.findByPublicId(publicId);
        Optional<Client> clientDb = clientRepository.findById(Long.parseLong(jwt.getSubject()));
        Appointment appointment = appointmentDb.get();

        if (!appointment.getClient().equals(clientDb.get())) {
            throw new  EmailAlreadyExistsException("a");
        }

        appointmentService.validateStatusTransition(appointment.getStatus(), AppointmentStatus.valueOf(dto.status()));

        appointment.setStatus(AppointmentStatus.valueOf(dto.status()));
        AppointmentCreateResponse response = new AppointmentCreateResponse(appointment.getPublicId(), appointment.getBarber().getName(), appointment.getDateTime(), appointment.getStatus(), appointment.getService().getName());
        appointmentRepository.save(appointment);

        return ResponseEntity.ok(response);
    }
}
