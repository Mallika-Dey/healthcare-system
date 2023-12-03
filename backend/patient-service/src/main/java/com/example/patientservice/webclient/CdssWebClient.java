package com.example.patientservice.webclient;

import com.example.patientservice.dto.request.ProgressRequestDTO;
import com.example.patientservice.dto.response.ProxyResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface CdssWebClient {
    public Mono<ProxyResponseDTO> addHealthProgress(@RequestBody ProgressRequestDTO progressRequestDTO);
}
