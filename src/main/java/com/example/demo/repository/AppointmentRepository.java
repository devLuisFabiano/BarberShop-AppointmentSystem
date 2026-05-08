package com.example.demo.repository;

import com.example.demo.model.Appointment;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public List<Appointment> findByUser(User user);
    @Modifying
    @Query("DELETE FROM appointment a WHERE a.id = :id")
    void deleteAppointmentById(@Param("id") Long id);
}
