package com.example.adminservice.controller;

import com.example.adminservice.dto.request.RequestDTO;
import com.example.adminservice.dto.response.AuthenticationResponseDTO;
import com.example.adminservice.response.ResponseHandler;
import com.example.adminservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RequestDTO request) {
        AuthenticationResponseDTO responseDTO = authenticationService.register(request);
        return ResponseHandler.generateResponse(new Date(), "Registration Successful", HttpStatus.OK, responseDTO);
    }
}
