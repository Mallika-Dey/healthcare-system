package com.example.doctorservice.service;

import com.example.doctorservice.dto.response.AllDoctorsDTO;
import com.example.doctorservice.dto.response.DoctorInfoDTO;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface DoctorService {
    public List<AllDoctorsDTO> getAllDoctors();

    public List<AllDoctorsDTO> getAllDoctorsByDeptId(int deptId);

    public DoctorInfoDTO getDoctorByUserId(Long userId);

    public List<AllDoctorsDTO> searchDoctors(String name, String department,
                                             String designation, Integer experience, String specialization);

    public void updateAvailability(Long doctorId, boolean available);

    public boolean isDoctorExist(Long userId);

    public Doctor getDoctor(Long doctorUserId);

    public long countDoctors();
}
