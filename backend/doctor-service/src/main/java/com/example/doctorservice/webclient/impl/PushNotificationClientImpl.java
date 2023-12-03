package com.example.doctorservice.webclient.impl;

import com.example.doctorservice.dto.request.NotificationDTO;
import com.example.doctorservice.dto.response.ProxyResponseDTO;
import com.example.doctorservice.utils.AppConstant;
import com.example.doctorservice.webclient.PushNotificationClient;
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
 * Implementation of the PushNotificationClient interface that handles communication
 * with notification service.
 * It uses WebClient to make call including retry mechanism and a circuit breaker.
 */
@Service
@Slf4j
public class PushNotificationClientImpl implements PushNotificationClient {
    private final WebClient.Builder webclient;

    public PushNotificationClientImpl(WebClient.Builder webClientBuilder) {
        this.webclient = webClientBuilder.baseUrl("http://localhost:8088");
    }

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "saveNotificationFallback")
    @Override
    public Mono<ProxyResponseDTO> saveNotification(NotificationDTO notificationDTO) {
        log.info("Attempting to save notification: {}", notificationDTO);
        return webclient
                .build()
                .post()
                .uri("/api/v2/notification/proxy/save")
                .bodyValue(notificationDTO)
                .retrieve()
                .bodyToMono(ProxyResponseDTO.class)
                .retryWhen(Retry.backoff(AppConstant.MAX_RETRY_ATTEMPTS, Duration.ofSeconds(AppConstant.RETRY_TIME_GAP))
                        .filter(this::retryableException)
                        .doBeforeRetry(retrySignal -> log.info("Retrying saveNotification attempt: {}, due to: {}",
                                retrySignal.totalRetries() + 1, retrySignal.failure())))
                .onErrorResume(ex -> {
                    log.error("Error while saving notification: {}", ex.getMessage(), ex);
                    return saveNotificationFallback(notificationDTO, ex);
                });
    }

    private boolean retryableException(Throwable ex) {
        if (ex instanceof WebClientResponseException responseException) {
            return responseException.getStatusCode().is5xxServerError();
        }
        return ex instanceof WebClientRequestException;
    }

    private Mono<? extends ProxyResponseDTO> saveNotificationFallback(NotificationDTO notificationDTO, Throwable ex) {
        log.warn("Executing fallback method for saveNotification due to exception: {}", ex.getMessage());
        return Mono.just(new ProxyResponseDTO("Fallback: Service can't receive data." +
                " Server down or error occurred."));
    }
}
