package com.barbershop.restfulapi.service;

import com.barbershop.restfulapi.config.InvalidStatusTransitionException;
import com.barbershop.restfulapi.model.enums.AppointmentStatus;
import com.barbershop.restfulapi.repository.AppointmentRepository;
import com.barbershop.restfulapi.repository.BarberRepository;
import com.barbershop.restfulapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final BarberRepository barberRepository;

    public void validateStatusTransition(AppointmentStatus current, AppointmentStatus target) {
        boolean valid = switch (current) {
            case SCHEDULED -> target == AppointmentStatus.CANCELLED || target == AppointmentStatus.COMPLETED;
            case CANCELLED, COMPLETED -> false; // terminal states, no further transitions
        };

        if (!valid) {
            throw new InvalidStatusTransitionException(current, target);
        }
    }
}
