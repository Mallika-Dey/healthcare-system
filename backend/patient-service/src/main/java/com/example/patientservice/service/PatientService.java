package com.example.patientservice.service;

import com.example.patientservice.dto.response.PatientCountDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    public List<PatientResponseDTO> getAll();

    public PatientResponseDTO getByUserId(Long userId);

    public PatientResponseDTO getPatientData(Long userId);

    public List<PatientResponseDTO> search(String bloodGroup);

    public String getBloodGroup(Long userid);

    public PatientCountDTO countInfo();
}
