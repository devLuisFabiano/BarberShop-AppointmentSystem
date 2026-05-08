package com.example.demo.config;

import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/profile").hasAnyRole(Role.USER.name(),  Role.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/appointment/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            } else {
                                response.sendRedirect("/profile");
                            }
                        })
                        .permitAll()
                )
                //.formLogin(form -> form.defaultSuccessUrl("/profile", true))
                .logout(config -> config.logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
