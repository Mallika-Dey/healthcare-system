package com.example.doctorservice.service;

import com.example.doctorservice.dto.response.AllDoctorsDTO;
import com.example.doctorservice.dto.response.DoctorInfoDTO;
import com.example.doctorservice.dto.response.RoomResponseDTO;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.enums.Degree;
import com.example.doctorservice.enums.Designation;
import com.example.doctorservice.exception.CustomException;
import com.example.doctorservice.feignclient.AdminServiceClient;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AdminServiceClient adminServiceClient;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Doctor createDoctor(Long doctorUserId) {
        return Doctor.builder()
                .userId(doctorUserId)
                .deptId(123)
                .roomId(456)
                .name("Dr. John Doe")
                .image("doctor_image_url.jpg")
                .departmentName("Cardiology")
                .medicalName("City Hospital")
                .degree(Degree.MD)
                .designation(Designation.SENIOR_CONSULTANT)
                .specialization("Cardiology")
                .yearOfExperience(10)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .noOfDailyPatient(30)
                .available(true)
                .build();
    }

    @Test
    public void getAllDoctors_ReturnsAllDoctors() {
        List<Doctor> mockedDoctors = Arrays.asList(createDoctor(1L), createDoctor(2L));
        when(doctorRepository.findAll()).thenReturn(mockedDoctors);

        List<AllDoctorsDTO> result = doctorService.getAllDoctors();

        assertEquals(mockedDoctors.size(), result.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    public void getAllDoctorsByDeptId_ValidDept_ReturnsDoctors() {
        int deptId = 1;
        List<Doctor> mockedDoctors = List.of(createDoctor(1L));
        when(adminServiceClient.departmentExist(deptId)).thenReturn(true);
        when(doctorRepository.findByDeptId(deptId)).thenReturn(mockedDoctors);

        List<AllDoctorsDTO> result = doctorService.getAllDoctorsByDeptId(deptId);

        assertEquals(mockedDoctors.size(), result.size());
    }

    @Test
    public void getAllDoctorsByDeptId_InvalidDept_ThrowsException() {
        int deptId = 1;
        when(adminServiceClient.departmentExist(deptId)).thenReturn(false);

        CustomException thrown = assertThrows(
                CustomException.class,
                () -> doctorService.getAllDoctorsByDeptId(deptId),
                "Expected getAllDoctorsByDeptId to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Failed to fetch department information"));
    }

    @Test
    public void getDoctorByUserId_ValidUserId_ReturnsDoctorInfo() {
        Long userId = 1L;
        Doctor mockedDoctor = createDoctor(userId);
        RoomResponseDTO mockedRoomResponse = new RoomResponseDTO();

        when(doctorRepository.findByUserId(userId)).thenReturn(Optional.of(mockedDoctor));
        when(adminServiceClient.getRoomInformation(anyInt())).thenReturn(mockedRoomResponse);

        DoctorInfoDTO result = doctorService.getDoctorByUserId(userId);

        assertNotNull(result);
    }

    @Test
    public void getDoctorByUserId_InvalidUserId_ThrowsException() {
        Long userId = 1L;
        when(doctorRepository.findByUserId(userId)).thenReturn(Optional.empty());

        CustomException thrown = assertThrows(
                CustomException.class,
                () -> doctorService.getDoctorByUserId(userId),
                "Expected getDoctorByUserId to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("doctor doesn't exist"));
    }
}
