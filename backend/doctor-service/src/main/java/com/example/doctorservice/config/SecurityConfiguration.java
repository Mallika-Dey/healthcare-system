package com.example.doctorservice.config;

import com.example.doctorservice.security.JwtAuthenticationFilter;
import com.example.doctorservice.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, AppConstant.DOCTOR_REGISTER).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_GET_ALL).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_DEPARTMENT).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_BY_ID).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_AVAILABLE).hasRole(AppConstant.ROLE_DOCTOR)
                        .requestMatchers(AppConstant.DOCTOR_SEARCH).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_APPOINTMENT_TAKE).hasRole(AppConstant.ROLE_PATIENT)
                        .requestMatchers(AppConstant.DOCTOR_APPOINTMENT_GET).hasRole(AppConstant.ROLE_DOCTOR)
                        .requestMatchers(AppConstant.PATIENT_UPCOMING_APPOINTMENTS).hasRole(AppConstant.ROLE_PATIENT)
                        .requestMatchers(AppConstant.DOCTOR_FREE_SLOT).permitAll()
                        .requestMatchers(AppConstant.DOCTOR_DASHBOARD).hasRole(AppConstant.ROLE_DOCTOR)
                        .requestMatchers(AppConstant.PROXY_API).permitAll()
                        .requestMatchers(AppConstant.ACTUATOR_API).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}

