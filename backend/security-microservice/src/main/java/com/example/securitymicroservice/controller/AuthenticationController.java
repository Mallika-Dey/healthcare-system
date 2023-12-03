package com.example.securitymicroservice.controller;


import com.example.securitymicroservice.DTO.response.AuthenticationResponseDTO;
import com.example.securitymicroservice.DTO.request.LogInRequestDTO;
import com.example.securitymicroservice.DTO.request.RegisterRequestDTO;
import com.example.securitymicroservice.response.ResponseHandler;
import com.example.securitymicroservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/v2/auth/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody LogInRequestDTO request) {
        return ResponseHandler.generateResponse(new Date(), "Login successful"
                , HttpStatus.OK, authenticationService.authenticate(request));
    }
}
