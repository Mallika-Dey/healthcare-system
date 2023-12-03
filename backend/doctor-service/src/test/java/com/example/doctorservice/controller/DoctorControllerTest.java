package com.example.doctorservice.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.doctorservice.dto.response.DoctorInfoDTO;
import com.example.doctorservice.service.AuthenticationService;
import com.example.doctorservice.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class DoctorControllerTest {
    @Mock
    private DoctorService doctorService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDoctors() {
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = doctorController.getAllDoctors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService).getAllDoctors();
    }

    @Test
    public void testGetAllDoctorsByDeptId() {
        int deptId = 1;
        when(doctorService.getAllDoctorsByDeptId(deptId)).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = doctorController.getAllDoctorsByDeptId(deptId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService).getAllDoctorsByDeptId(deptId);
    }

    @Test
    public void testGetDoctorByUserId() {
        Long doctorId = 1L;
        when(doctorService.getDoctorByUserId(doctorId)).thenReturn(new DoctorInfoDTO());
        ResponseEntity<?> response = doctorController.getDoctorByUserId(doctorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService).getDoctorByUserId(doctorId);
    }

    @Test
    public void testUpdateAvailable() {
        boolean available = true;
        Long doctorId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(doctorId);
        ResponseEntity<?> response = doctorController.updateAvailable(available);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService).updateAvailability(doctorId, available);
    }

    @Test
    public void testSearchDoctors() {
        String name = "Test Name";
        String department = "Test Department";
        String designation = "Test Designation";
        Integer experience = 10;
        String specialization = "Test Specialization";
        when(doctorService.searchDoctors(name, department, designation, experience, specialization)).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = doctorController.searchDoctors(name, department, designation, experience, specialization);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService).searchDoctors(name, department, designation, experience, specialization);
    }
}
