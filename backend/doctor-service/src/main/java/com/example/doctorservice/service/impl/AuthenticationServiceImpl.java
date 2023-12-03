package com.example.doctorservice.service.impl;

import com.example.doctorservice.dto.request.RegisterFeignDTO;
import com.example.doctorservice.dto.request.RegisterRequestDTO;
import com.example.doctorservice.dto.request.RoomUnavailableRequestDTO;
import com.example.doctorservice.dto.response.AuthenticationResponseDTO;
import com.example.doctorservice.dto.response.RegisterResponseDTO;
import com.example.doctorservice.dto.response.RoomProxyResponseDTO;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.enums.Degree;
import com.example.doctorservice.enums.Designation;
import com.example.doctorservice.exception.AuthenticationException;
import com.example.doctorservice.exception.CustomException;
import com.example.doctorservice.exception.FeignCustomException;
import com.example.doctorservice.feignclient.AdminServiceClient;
import com.example.doctorservice.feignclient.SecurityServiceClient;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.service.AuthenticationService;
import com.example.doctorservice.utils.AppConstant;
import com.example.doctorservice.utils.EnumValidator;
import com.example.doctorservice.webclient.AdminWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AdminServiceClient adminServiceClient;
    private final SecurityServiceClient securityServiceClient;
    private final AdminWebClient adminWebClient;
    private final DoctorRepository doctorRepository;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        log.info("Attempt to register doctor.........");
        LocalTime endTime = calculateEndTime(request.getStartTime(), request.getNoOfDailyPatient());

        validateDegreeAndDesignation(request);

        RoomProxyResponseDTO roomProxyResponseDTO = assignRoomForDoctor(request.getDepartmentName());

        AuthenticationResponseDTO authenticationResponseDTO = registerDoctor(request, roomProxyResponseDTO);
        Doctor doctor = mapToDoctor(request, authenticationResponseDTO.getUserId(), roomProxyResponseDTO, endTime);
        doctorRepository.save(doctor);
        return mapToRegistrationResponseDTO(doctor, authenticationResponseDTO);
    }

    private void validateDegreeAndDesignation(RegisterRequestDTO request) {
        log.info("validating degree and designations");
        EnumValidator.validateDesignation(request.getDesignation());
        EnumValidator.validateDegree(request.getDegree());
    }

    private RegisterResponseDTO mapToRegistrationResponseDTO(Doctor doctor,
                                                             AuthenticationResponseDTO authenticationResponseDTO) {
        return RegisterResponseDTO
                .builder()
                .userId(authenticationResponseDTO.getUserId())
                .role(authenticationResponseDTO.getRole())
                .token(authenticationResponseDTO.getToken())
                .roomId(doctor.getRoomId())
                .startTime(doctor.getStartTime())
                .endTime(doctor.getEndTime())
                .build();
    }

    private AuthenticationResponseDTO registerDoctor(RegisterRequestDTO request,
                                                     RoomProxyResponseDTO roomProxyResponseDTO) {
        try {
            return securityServiceClient.register(mapToFeignRegisterDTO(request));
        } catch (FeignCustomException exception) {
            log.error("Error during register doctor", exception);
            performRollBack(roomProxyResponseDTO.getRoomId());
            throw exception;
        }
    }

    private void performRollBack(int roomId) {
        adminWebClient.makeRoomAvailable(new RoomUnavailableRequestDTO(roomId))
                .subscribe(
                        response -> log.info("Rollback successful"),
                        ex -> log.error("Rollback unsuccessful: " + ex.getMessage())
                );
    }

    private RegisterFeignDTO mapToFeignRegisterDTO(RegisterRequestDTO request) {
        return RegisterFeignDTO.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AppConstant.ROLE_DOCTOR).build();
    }

    private RoomProxyResponseDTO assignRoomForDoctor(String departmentName) {
        try {
            return adminServiceClient.assignDoctorDeptAndRoom(departmentName);
        } catch (FeignCustomException exception) {
            log.error("Error during room and department assignment", exception);
            throw exception;
        }
    }

    private LocalTime calculateEndTime(LocalTime startTime, int noOfPatient) {
        LocalTime lastTime = LocalTime.of(23, 59);
        long minutes = Duration.between(startTime, lastTime).toMinutes();
        long totalTimesPerDay = noOfPatient * AppConstant.TIME_PER_PATIENT;

        if (totalTimesPerDay > minutes) {
            throw new CustomException(new Date(), "The calculated end time exceeds allowable hours", HttpStatus.BAD_REQUEST);
        }
        return startTime.plusMinutes(totalTimesPerDay);
    }

    private Doctor mapToDoctor(RegisterRequestDTO request, Long userId, RoomProxyResponseDTO roomProxyResponseDTO, LocalTime endTime) {
        return Doctor
                .builder()
                .deptId(roomProxyResponseDTO.getDeptId())
                .userId(userId)
                .roomId(roomProxyResponseDTO.getRoomId())
                .name(request.getName())
                .image(request.getImage())
                .departmentName(request.getDepartmentName())
                .medicalName(request.getMedicalName())
                .degree(Degree.valueOf(request.getDegree()))
                .designation(Designation.valueOf(request.getDesignation()))
                .specialization(request.getSpecialization())
                .yearOfExperience(request.getYearOfExperience())
                .startTime(request.getStartTime())
                .endTime(endTime)
                .noOfDailyPatient(request.getNoOfDailyPatient())
                .build();
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}