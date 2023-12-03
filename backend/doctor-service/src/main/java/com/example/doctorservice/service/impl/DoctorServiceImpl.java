package com.example.doctorservice.service.impl;

import com.example.doctorservice.dto.response.AllDoctorsDTO;
import com.example.doctorservice.dto.response.DoctorInfoDTO;
import com.example.doctorservice.dto.response.RoomResponseDTO;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.exception.CustomException;
import com.example.doctorservice.feignclient.AdminServiceClient;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.repository.DoctorSpecification;
import com.example.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final AdminServiceClient adminServiceClient;

    @Override
    public List<AllDoctorsDTO> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    private AllDoctorsDTO mapToDTO(Doctor doctor) {
        return AllDoctorsDTO
                .builder()
                .deptId(doctor.getDeptId())
                .roomId(doctor.getRoomId())
                .userId(doctor.getUserId())
                .name(doctor.getName())
                .image(doctor.getImage())
                .departmentName(doctor.getDepartmentName())
                .medicalName(doctor.getMedicalName())
                .degree(doctor.getDegree().toString())
                .designation(doctor.getDesignation().toString())
                .specialization(doctor.getSpecialization())
                .yearOfExperience(doctor.getYearOfExperience())
                .startTime(doctor.getStartTime())
                .endTime(doctor.getEndTime())
                .noOfDailyPatient(doctor.getNoOfDailyPatient())
                .available(doctor.isAvailable())
                .build();
    }

    @Override
    public List<AllDoctorsDTO> getAllDoctorsByDeptId(int deptId) {
        log.info("Fetching all doctors for department ID: {}", deptId);

        if (!adminServiceClient.departmentExist(deptId)) {
            log.error("Failed to fetch department information for department ID: {}", deptId);
            throw new CustomException(new Date(), "Failed to fetch department information", HttpStatus.NOT_FOUND);
        }
        return doctorRepository.findByDeptId(deptId).stream().map(this::mapToDTO).toList();
    }

    @Override
    public DoctorInfoDTO getDoctorByUserId(Long userId) {
        log.info("Fetching doctor details for user ID: {}", userId);
        Doctor doctor = getDoctor(userId);

        RoomResponseDTO roomResponseDTO = adminServiceClient.getRoomInformation(doctor.getRoomId());

        return DoctorInfoDTO
                .builder()
                .userId(doctor.getUserId())
                .roomNumber(roomResponseDTO.getRoomNumber())
                .name(doctor.getName())
                .image(doctor.getImage())
                .departmentName(roomResponseDTO.getDeptName())
                .medicalName(doctor.getMedicalName())
                .degree(doctor.getDegree().toString())
                .designation(doctor.getDesignation().toString())
                .specialization(doctor.getSpecialization())
                .yearOfExperience(doctor.getYearOfExperience())
                .startTime(doctor.getStartTime())
                .endTime(doctor.getEndTime())
                .noOfDailyPatient(doctor.getNoOfDailyPatient())
                .available(doctor.isAvailable())
                .build();
    }

    @Override
    public Doctor getDoctor(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(new Date(), "doctor doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public long countDoctors() {
        return doctorRepository.count();
    }

    @Override
    public boolean isDoctorExist(Long doctorId) {
        log.info("Checking if doctor exists with ID: {}", doctorId);
        getDoctor(doctorId);
        return true;
    }

    @Override
    public List<AllDoctorsDTO> searchDoctors(String name, String department,
                                             String designation, Integer experience, String specialization) {
        log.info("Trying to search doctors based on parameters...........");
        List<Doctor> doctors = doctorRepository.findAll(DoctorSpecification.dynamicQuery(name, department, designation, experience, specialization));
        return doctors.stream().map(this::mapToDTO).toList();
    }

    @Override
    public void updateAvailability(Long userId, boolean available) {
        log.info("Updating availability for doctor with user ID: {} to {}", userId, available);
        Doctor doctor = getDoctor(userId);
        doctor.setAvailable(available);
        doctorRepository.save(doctor);
        log.info("Updated availability for doctor with user ID: {}", userId);
    }
}
