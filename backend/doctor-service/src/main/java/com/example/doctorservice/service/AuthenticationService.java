package com.example.doctorservice.service;


import com.example.doctorservice.dto.request.RegisterRequestDTO;
import com.example.doctorservice.dto.response.RegisterResponseDTO;

public interface AuthenticationService {
    public RegisterResponseDTO register(RegisterRequestDTO request);
    public Long getAuthenticatedUserId();
}
