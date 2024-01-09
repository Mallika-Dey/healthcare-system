package com.example.patientservice.feignclient;

import com.example.patientservice.dto.request.RegisterFeignDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "security-service", configuration = CustomFeignErrorDecoder.class)
public interface SecurityServiceClient {
    @PostMapping("/api/v2/auth/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterFeignDTO request);
}
