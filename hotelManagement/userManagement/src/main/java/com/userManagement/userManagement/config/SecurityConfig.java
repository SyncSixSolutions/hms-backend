package com.userManagement.userManagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/customer/keycloak-login",   // Allow login endpoint
                                "/api/users/customer/register",         // Allow registration
                                "/api/users/receptionist/register", //For receptionist registration
                                "/api/users/receptionist/login", //For receptionist login3
                                "/v3/api-docs/**",            // Swagger docs (optional)
                                "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers("/user/**").hasRole("user") // Require role for /user/**
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtConfig()))); // Enable JWT resource server

        return http.build();
    }
}
