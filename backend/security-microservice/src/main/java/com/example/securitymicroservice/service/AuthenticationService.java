package com.example.securitymicroservice.service;

import com.example.securitymicroservice.DTO.request.LogInRequestDTO;
import com.example.securitymicroservice.DTO.request.RegisterRequestDTO;
import com.example.securitymicroservice.DTO.response.AuthenticationResponseDTO;
import com.example.securitymicroservice.DTO.response.LogInResponseDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO register(RegisterRequestDTO request);
    public LogInResponseDTO authenticate(LogInRequestDTO request);
}
