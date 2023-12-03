package com.example.adminservice.service;

import com.example.adminservice.dto.request.RegisterRequestDTO;
import com.example.adminservice.dto.request.RequestDTO;
import com.example.adminservice.dto.response.AuthenticationResponseDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO register(RequestDTO request);
}
