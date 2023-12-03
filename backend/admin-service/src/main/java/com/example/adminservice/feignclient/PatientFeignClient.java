package com.example.adminservice.feignclient;

import com.example.adminservice.dto.response.PatientCountDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PATIENT-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface PatientFeignClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "countPatientFallback")
    @GetMapping("/api/v2/patient/proxy/count")
    public PatientCountDTO countPatient();

    default public PatientCountDTO countPatientFallback(Throwable throwable) {
        return new PatientCountDTO(0,0);
    }
}
