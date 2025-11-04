package com.js.Paws_Cares_Portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // allow all routes for now
            )
            .csrf(csrf -> csrf.disable()) // disable CSRF for development
            .formLogin(form -> form.disable()); // disable default login page

        return http.build();
    }
}

