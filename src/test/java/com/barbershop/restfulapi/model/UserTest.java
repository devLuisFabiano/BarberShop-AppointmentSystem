package com.barbershop.restfulapi.model;

import com.barbershop.restfulapi.model.entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void deveCriarUsuarioComConstrutor(){
        User user = new User("Luis", "luis@gmail.com", "123", Role.USER.name());
        assertEquals("Luis", user.getName());
        assertEquals("123", user.getPassword());
        assertEquals("luis@gmail.com", user.getEmail());
        assertEquals("USER", user.getRole());
    }

    @Test
    void deveAlterarDadosDoUsuario(){
        User user = new User();
        user.setName("Luis");
        user.setPassword("123");
        user.setEmail("luis@gmail.com");
        user.setRole(Role.USER.name());

        assertEquals("Luis", user.getName());
        assertEquals("123", user.getPassword());
        assertEquals("luis@gmail.com", user.getEmail());
        assertEquals("USER", user.getRole());
    }
}
