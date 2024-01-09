package com.example.patientservice.webclient.impl;

import com.example.patientservice.dto.request.RegisterFeignDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;
import com.example.patientservice.webclient.SecurityWebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SecurityWebClientImpl implements SecurityWebClient {
    @Autowired
    private WebClient.Builder webClient;

//    not working
//    @Autowired
//    public SecurityWebClientImpl(WebClient.Builder webClient) {
//        this.webClient = webClient.baseUrl("");
//        this.webClient = webClient.baseUrl("http://localhost:8081");
//    }

    @CircuitBreaker(name = "CircuitBreakerService")
    @Override
    public AuthenticationResponseDTO register(RegisterFeignDTO request) {
        return this.webClient
                .build()
                .post()
                .uri("http://SECURITY-SERVICE/api/v2/auth/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthenticationResponseDTO.class)
                .block();
    }
}
