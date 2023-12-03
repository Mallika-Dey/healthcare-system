package com.example.doctorservice.webclient.impl;

import com.example.doctorservice.dto.request.RoomUnavailableRequestDTO;
import com.example.doctorservice.dto.response.ProxyResponseDTO;
import com.example.doctorservice.utils.AppConstant;
import com.example.doctorservice.webclient.AdminWebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * Implementation of the AdminWebClient interface handling communication
 * with an admin service.
 * It utilizes WebClient for HTTP operations and includes a retry mechanism
 * and circuit breaker for resilience.
 */
@Service
@Slf4j
public class AdminWebClientImpl implements AdminWebClient {
    private final WebClient.Builder webclient;

    public AdminWebClientImpl(WebClient.Builder webClientBuilder) {
        this.webclient = webClientBuilder.baseUrl("http://localhost:8081");
    }

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "makeRoomAvailableFallback")
    @Override
    public Mono<ProxyResponseDTO> makeRoomAvailable(RoomUnavailableRequestDTO room) {
        log.info("Attempting to make the unavailable room available: {}", room);

        return webclient
                .build()
                .put()
                .uri("/api/v2/admin/proxy/room/unavailable")
                .bodyValue(room)
                .retrieve()
                .bodyToMono(ProxyResponseDTO.class)
                .retryWhen(Retry.backoff(AppConstant.MAX_RETRY_ATTEMPTS, Duration.ofSeconds(AppConstant.RETRY_TIME_GAP))
                        .filter(this::retryableException)
                        .doBeforeRetry(retrySignal -> log.info("Retrying makeRoomAvailable attempt: {}, due to: {}",
                                retrySignal.totalRetries() + 1, retrySignal.failure()))
                )
                .onErrorResume(ex -> {
                    log.error("Error while making room available: {}", ex.getMessage(), ex);
                    return makeRoomAvailableFallback(room, ex);
                });
    }

    private boolean retryableException(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            return responseException.getStatusCode().is5xxServerError();
        }
        return ex instanceof WebClientRequestException;
    }

    private Mono<? extends ProxyResponseDTO> makeRoomAvailableFallback(RoomUnavailableRequestDTO room, Throwable ex) {
        return Mono.just(new ProxyResponseDTO("Fallback: Service can't receive data." +
                " Server down or error occurred."));
    }
}
