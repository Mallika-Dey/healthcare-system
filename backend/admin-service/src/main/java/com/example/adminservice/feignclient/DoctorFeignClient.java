package com.example.adminservice.feignclient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DOCTOR-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface DoctorFeignClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "countDoctorsFallback")
    @GetMapping("/api/v2/proxy/doctor/count")
    public long countDoctors();

    default public long countDoctorsFallback(Throwable throwable) {
        return 0;
    }
}
