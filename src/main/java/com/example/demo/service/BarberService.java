package com.example.demo.service;

import com.example.demo.model.Barber;
import com.example.demo.model.User;
import com.example.demo.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
@Service
public class BarberService{
    @Autowired
    private BarberRepository barberRepository;

    public Barber getBarberById(Long id){
        Optional<Barber> barber = barberRepository.findById(id);
        return barber.orElse(null);
    }

    public List<Barber> getAllBarbers(){
        return barberRepository.findAll();
    }

    public void checkIfBarberAlreadyExists (String barberEmail, BindingResult bindingResult){
        Barber barberExists = barberRepository.findByEmail(barberEmail);
        if(barberExists != null){
            bindingResult.addError(new FieldError(
                    "err",
                    "email",
                    "Email já cadastrado")
            );
        }
    }

    public void saveBarber(Barber barber, Model model){
        barberRepository.save(barber);
        model.addAttribute("success", true);
        model.addAttribute("employees", barberRepository.findAll());
    }

    public void deleteBarber(Long id, Model model){
        Barber barber = getBarberById(id);

        if(barber != null){
            barberRepository.deleteById(id);
            model.addAttribute("barber", barberRepository.findAll());
        }
    }
}
