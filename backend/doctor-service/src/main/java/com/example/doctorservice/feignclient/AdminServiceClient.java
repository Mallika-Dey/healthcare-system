package com.example.doctorservice.feignclient;

import com.example.doctorservice.dto.response.RoomProxyResponseDTO;
import com.example.doctorservice.dto.response.RoomResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "api-gateway", configuration = CustomFeignErrorDecoder.class)
public interface AdminServiceClient {
    @CircuitBreaker(name = "CircuitBreakerService")
    @PutMapping("/admin-service/api/v2/admin/proxy/assignDoctor/{departmentName}")
    public RoomProxyResponseDTO assignDoctorDeptAndRoom(@PathVariable("departmentName") String departmentName);

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "getRoomInformationFallback")
    @GetMapping("/admin-service/api/v2/admin/proxy/roomInfo/{roomId}")
    public RoomResponseDTO getRoomInformation(@PathVariable("roomId") Integer roomId);

    default public RoomResponseDTO getRoomInformationFallback(@PathVariable Integer roomId, Throwable throwable) {
        return RoomResponseDTO
                .builder()
                .roomNumber(0)
                .deptName("Not known")
                .available(false)
                .build();
    }

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "departmentNotExistFallback")
    @GetMapping("/admin-service/api/v2/admin/proxy/get/{departmentId}")
    public boolean departmentExist(@PathVariable int departmentId);

    default public boolean departmentExistFallback(@PathVariable int departmentId, Throwable throwable) {
        return false;
    }
}
