package com.example.demo.service;

import com.example.demo.model.Barber;
import com.example.demo.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class AdminService {

    @Autowired
    private BarberRepository barberRepository;


}
