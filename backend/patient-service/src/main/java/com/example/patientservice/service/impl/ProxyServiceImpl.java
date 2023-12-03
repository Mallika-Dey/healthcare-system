package com.example.patientservice.service.impl;

import com.example.patientservice.dto.response.PatientProxyDTO;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.ProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {
    private final PatientRepository patientRepository;

    @Override
    public List<PatientProxyDTO> getPatientListByUserId(List<Long> userIdList) {
        return patientRepository
                .findByUserIdIn(userIdList)
                .stream()
                .map(this::mapToDTO)
                .sorted(Comparator.comparing(PatientProxyDTO::getUserId))
                .toList();
    }

    private PatientProxyDTO mapToDTO(Patient patient) {
        return PatientProxyDTO
                .builder()
                .userId(patient.getUserId())
                .name(patient.getName())
                .imageUrl(patient.getImageUrl())
                .build();
    }

}
