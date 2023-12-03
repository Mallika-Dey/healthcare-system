package com.example.doctorservice.controller;

import com.example.doctorservice.dto.request.RegisterRequestDTO;
import com.example.doctorservice.dto.response.RegisterResponseDTO;
import com.example.doctorservice.response.ResponseHandler;
import com.example.doctorservice.service.AuthenticationService;
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
@RequestMapping("/api/v1/doctor")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO responseDTO = authenticationService.register(request);
        return ResponseHandler.generateResponse(new Date(), "Registration Successful", HttpStatus.OK, responseDTO);
    }
}
