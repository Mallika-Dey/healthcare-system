package com.example.patientservice.controller;

import com.example.patientservice.dto.request.RegisterRequestDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;
import com.example.patientservice.response.ResponseHandler;
import com.example.patientservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthenticationResponseDTO responseDTO = authenticationService.registerPatient(request);
        return ResponseHandler.generateResponse(new Date(), "Registration Successful", HttpStatus.OK, responseDTO);
    }
}
