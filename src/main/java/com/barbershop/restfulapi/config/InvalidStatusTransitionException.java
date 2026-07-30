package com.barbershop.restfulapi.config;

import com.barbershop.restfulapi.model.enums.AppointmentStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(AppointmentStatus current, AppointmentStatus target) {
        super("Cannot change status from " + current + " to " + target);
    }
}