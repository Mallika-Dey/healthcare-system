package com.example.patientservice.service;

import com.example.patientservice.dto.request.RegisterRequestDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO registerPatient(RegisterRequestDTO registerRequestDTO);

    public Long getAuthenticatedUserId();

    public String getTokenFromPrincipal();

    public void handleAccessByUserRole(Long userId, String role);
}
