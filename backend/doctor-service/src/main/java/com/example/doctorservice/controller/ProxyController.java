package com.example.doctorservice.controller;

import com.example.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/proxy/doctor")
public class ProxyController {
    private final DoctorService doctorService;

    @GetMapping("/{doctorId}")
    public boolean isDoctorExistById(@PathVariable Long doctorId) {
        return doctorService.isDoctorExist(doctorId);
    }

    @GetMapping("/count")
    public long countDoctors() {
        return doctorService.countDoctors();
    }
}
