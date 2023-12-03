package com.example.adminservice.service.impl;

import com.example.adminservice.dto.response.DashBoardDTO;
import com.example.adminservice.dto.response.PatientCountDTO;
import com.example.adminservice.feignclient.DoctorFeignClient;
import com.example.adminservice.feignclient.PatientFeignClient;
import com.example.adminservice.repository.DepartmentRepository;
import com.example.adminservice.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final PatientFeignClient patientFeignClient;
    private final DoctorFeignClient doctorFeignClient;
    private final DepartmentRepository departmentRepository;

    @Override
    public DashBoardDTO getDashBoardInfo() {
        PatientCountDTO patientCountDTO = patientFeignClient.countPatient();
        long totalDoctor=doctorFeignClient.countDoctors();
        return DashBoardDTO
                .builder()
                .patientCount(patientCountDTO.getPatientCount())
                .totalDonor(patientCountDTO.getTotalDonor())
                .totalDoctor(totalDoctor)
                .totalDepartment(departmentRepository.count())
                .build();
    }
}
