package com.example.doctorservice.feignclient;

import com.example.doctorservice.dto.response.PatientProxyDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PATIENT-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface PatientFeignClient {
    @CircuitBreaker(name = "CircuitBreakerService")
    @GetMapping("/api/v2/patient/proxy/getByUserIdList")
    public List<PatientProxyDTO> getByUserId(@RequestParam("patients") List<Long> patients);
}
