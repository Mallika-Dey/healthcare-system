package com.example.patientservice.service;

import com.example.patientservice.dto.response.PatientProxyDTO;

import java.util.List;

public interface ProxyService {
    public List<PatientProxyDTO> getPatientListByUserId(List<Long> userIdList);
}
