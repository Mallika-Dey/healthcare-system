package com.example.adminservice.service.impl;

import com.example.adminservice.dto.request.RegisterRequestDTO;
import com.example.adminservice.dto.request.RequestDTO;
import com.example.adminservice.dto.response.AuthenticationResponseDTO;
import com.example.adminservice.exception.FeignCustomException;
import com.example.adminservice.feignclient.SecurityServiceClient;
import com.example.adminservice.service.AuthenticationService;
import com.example.adminservice.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final SecurityServiceClient securityServiceClient;

    @Override
    public AuthenticationResponseDTO register(RequestDTO request) {
        RegisterRequestDTO requestData = RegisterRequestDTO
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AppConstant.ROLE_ADMIN)
                .build();

        try {
            return securityServiceClient.register(requestData);
        } catch (FeignCustomException ex) {
            log.error("Registration failed ", ex);
            throw ex;
        }
    }
}
