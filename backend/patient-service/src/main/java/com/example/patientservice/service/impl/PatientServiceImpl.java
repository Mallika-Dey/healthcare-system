package com.example.patientservice.service.impl;

import com.example.patientservice.dto.response.PatientCountDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.enums.BloodGroup;
import com.example.patientservice.exception.CustomException;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.repository.PatientSpecification;
import com.example.patientservice.service.AuthenticationService;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<PatientResponseDTO> getAll() {
        log.info("Getting all patients");
        return patientRepository.findAll().stream().map(this::mapToPatientResponseDTO).toList();
    }

    @Override
    public PatientResponseDTO getByUserId(Long userId) {
        authenticationService.handleAccessByUserRole(userId, "ADMIN");
        log.info("Getting patient by user ID: {}", userId);
        return getPatientData(userId);
    }

    public PatientResponseDTO getPatientData(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Patient not found");
                    return new CustomException(new Date(), "Patient data", HttpStatus.NOT_FOUND);
                });
        return mapToPatientResponseDTO(patient);
    }

    @Override
    public List<PatientResponseDTO> search(String bloodGroup) {
        log.info("Searching patients by blood group: {}", bloodGroup);
        return patientRepository.findAll(PatientSpecification.dynamicQuery(bloodGroup))
                .stream().map(this::mapToPatientResponseDTO).toList();
    }

    @Override
    public String getBloodGroup(Long userId) {
        BloodGroup bloodGroup = patientRepository.findBloodGroupByUserId(userId);
        if (bloodGroup == null) {
            throw new CustomException(new Date(), "Failed to fetch blood group", HttpStatus.NOT_FOUND);
        }
        return bloodGroup.toString();
    }

    @Override
    public PatientCountDTO countInfo() {
        return PatientCountDTO
                .builder()
                .patientCount(patientRepository.count())
                .totalDonor(patientRepository.countByInterestedInBloodDonateTrue())
                .build();
    }

    private PatientResponseDTO mapToPatientResponseDTO(Patient patient) {
        return PatientResponseDTO
                .builder()
                .userId(patient.getUserId())
                .name(patient.getName())
                .imageUrl(patient.getImageUrl())
                .address(patient.getAddress())
                .mobileNo(patient.getMobileNo())
                .dateOfBirth(patient.getDateOfBirth())
                .bloodGroup(patient.getBloodGroup().toString())
                .interestedInBloodDonate(patient.getInterestedInBloodDonate())
                .build();
    }
}
