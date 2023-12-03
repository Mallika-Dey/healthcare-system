package com.example.communityservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface DoctorFeignClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "isDoctorExistByIdFallback")
    @GetMapping("/api/v2/proxy/doctor/{doctorId}")
    public boolean isDoctorExistById(@PathVariable Long doctorId);

    default public boolean isDoctorExistByIdFallback(@PathVariable Long doctorId, Throwable throwable) {
        return false;
    }
}
