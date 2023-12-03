package com.example.patientservice.service;

import com.example.patientservice.dto.request.HealthProfileDTO;
import com.example.patientservice.dto.response.HealthProfileResponseDTO;

public interface HealthProfileService {
    public void createHealthProfile(HealthProfileDTO healthProfileDTO, Long userId);

    public void updateHealthProfile(HealthProfileDTO healthProfileDTO, Long userId);

    public HealthProfileResponseDTO getUserHealthProfile(Long userId);
}
