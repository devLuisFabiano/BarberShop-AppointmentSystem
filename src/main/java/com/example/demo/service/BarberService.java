package com.example.demo.service;

import com.example.demo.model.Barber;
import com.example.demo.model.User;
import com.example.demo.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class BarberService{
    @Autowired
    private BarberRepository barberRepository;

    public  Barber getBarber(Long id){
        Optional<Barber> barber = barberRepository.findById(id);
        return barber.orElse(null);
    }
}
