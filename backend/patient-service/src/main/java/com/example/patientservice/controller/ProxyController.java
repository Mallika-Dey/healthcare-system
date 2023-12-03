package com.example.patientservice.controller;

import com.example.patientservice.dto.response.HealthProfileResponseDTO;
import com.example.patientservice.dto.response.PatientCountDTO;
import com.example.patientservice.dto.response.PatientProxyDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;
import com.example.patientservice.service.HealthProfileService;
import com.example.patientservice.service.PatientService;
import com.example.patientservice.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/patient/proxy")
public class ProxyController {
    private final PatientService patientService;
    private final HealthProfileService healthProfileService;
    private final ProxyService proxyService;

    @GetMapping("/getByUserId/{userId}")
    public PatientResponseDTO getByUserId(@PathVariable Long userId) {
        return patientService.getPatientData(userId);
    }

    @GetMapping("/get_health-profile/user/{userId}")
    public HealthProfileResponseDTO getHealthProfileByUserId(@PathVariable Long userId) {
        return healthProfileService.getUserHealthProfile(userId);
    }

    @GetMapping("/getByUserIdList")
    public List<PatientProxyDTO> getByUserId(@RequestParam("patients") List<Long> patients) {
        return proxyService.getPatientListByUserId(patients);
    }

    @GetMapping("/count")
    public PatientCountDTO countPatient() {
        return patientService.countInfo();
    }
}
