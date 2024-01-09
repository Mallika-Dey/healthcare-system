package com.example.patientservice.webclient;

import com.example.patientservice.dto.request.RegisterFeignDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface SecurityWebClient {
    public AuthenticationResponseDTO register(@RequestBody RegisterFeignDTO request);
}
