package com.barbershop.restfulapi.config;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
