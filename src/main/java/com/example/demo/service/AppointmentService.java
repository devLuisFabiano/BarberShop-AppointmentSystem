package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Barber;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<LocalTime> getTimeSlots(){
        return List.of(
                LocalTime.of(8, 0),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)
        );
    }

    public boolean appointmentExists(Barber barber, LocalDateTime date){
        return appointmentRepository.existsByBarberAndDateTime(barber, date);
    }

    public void saveAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public void deleteAppointmentById(long id){
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentByBarber(Barber barber){
        return appointmentRepository.findByBarber(barber);
    }
}
