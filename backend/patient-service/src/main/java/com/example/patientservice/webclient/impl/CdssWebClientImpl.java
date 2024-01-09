package com.example.patientservice.webclient.impl;

import com.example.patientservice.dto.request.ProgressRequestDTO;
import com.example.patientservice.dto.response.ProxyResponseDTO;
import com.example.patientservice.service.AuthenticationService;
import com.example.patientservice.utils.AppConstant;
import com.example.patientservice.webclient.CdssWebClient;
import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class CdssWebClientImpl implements CdssWebClient {
    @Autowired
    private WebClient.Builder webclient;
    @Autowired
    private AuthenticationService authenticationService;

//    public CdssWebClientImpl(WebClient.Builder webclient, AuthenticationService authenticationService) {
//        this.webclient = webclient.baseUrl("http://localhost:9090/cdss-service");
//        this.authenticationService = authenticationService;
//    }

    @CircuitBreaker(name = "CircuitBreakerService")
    @Override
    public Mono<ProxyResponseDTO> addHealthProgress(ProgressRequestDTO progressRequestDTO) {
        return webclient
                .build()
                .post()
                .uri("http://CDSS-SERVICE/api/v2/cdss/proxy/save")
                .header("Authorization", authenticationService.getTokenFromPrincipal())
                .bodyValue(progressRequestDTO)
                .retrieve()
                .bodyToMono(ProxyResponseDTO.class)
                .retryWhen(Retry.backoff(AppConstant.MAX_RETRY_ATTEMPTS, Duration.ofSeconds(20))
                        .filter(this::retryableException))
                .onErrorResume(ex -> addHealthProgressFallback(progressRequestDTO, ex));
    }

    private boolean retryableException(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            return responseException.getStatusCode().is5xxServerError();
        }
        return ex instanceof WebClientRequestException;
    }

    private Mono<? extends ProxyResponseDTO> addHealthProgressFallback(
            ProgressRequestDTO progressRequestDTO, Throwable ex) {
        return Mono.just(new ProxyResponseDTO("Fallback: Service can't receive data." +
                " Server down or error occurred."));
    }
}
