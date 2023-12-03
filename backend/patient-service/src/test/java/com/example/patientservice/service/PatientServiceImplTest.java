package com.example.patientservice.service;

import com.example.patientservice.dto.response.PatientResponseDTO;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.enums.BloodGroup;
import com.example.patientservice.exception.CustomException;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

public class PatientServiceImplTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBloodGroup_Success() {
        Long userId = 1L;
        BloodGroup mockBloodGroup = BloodGroup.A_POSITIVE;
        when(patientRepository.findBloodGroupByUserId(userId)).thenReturn(mockBloodGroup);

        String result = patientService.getBloodGroup(userId);

        assertNotNull(result);
        assertEquals("A_POSITIVE", result);
        verify(patientRepository).findBloodGroupByUserId(userId);
    }

    @Test
    public void testGetBloodGroup_NotFound() {
        Long userId = 2L;
        when(patientRepository.findBloodGroupByUserId(userId)).thenReturn(null);

        assertThrows(CustomException.class, () -> patientService.getBloodGroup(userId));
        verify(patientRepository).findBloodGroupByUserId(userId);
    }

    @Test
    public void testGetByUserId_Success() {
        Long userId = 1L;
        Patient mockPatient = Patient.builder()
                .userId(userId)
                .name("John Doe")
                .imageUrl("image_url")
                .address("123 Street")
                .mobileNo("123-456-7890")
                .dateOfBirth(LocalDate.of(1990,01,01))
                .bloodGroup(BloodGroup.A_POSITIVE)
                .interestedInBloodDonate(true)
                .build();

        doNothing().when(authenticationService).handleAccessByUserRole(userId, "ADMIN");
        when(patientRepository.findByUserId(userId)).thenReturn(Optional.of(mockPatient));

        PatientResponseDTO result = patientService.getByUserId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("John Doe", result.getName());
        assertEquals("image_url", result.getImageUrl());
        assertEquals("123 Street", result.getAddress());
        assertEquals("123-456-7890", result.getMobileNo());
        assertEquals(LocalDate.of(1990,01,01), result.getDateOfBirth());
        assertEquals("A_POSITIVE", result.getBloodGroup());
        assertTrue(result.isInterestedInBloodDonate());
    }

    @Test
    public void testGetByUserId_UnauthorizedAccess() {
        Long userId = 2L;
        doThrow(new RuntimeException("Unauthorized"))
                .when(authenticationService)
                .handleAccessByUserRole(userId, "ADMIN");

        assertThrows(RuntimeException.class, () -> patientService.getByUserId(userId));
    }

    @Test
    public void testGetByUserId_NoDataFound() {
        Long userId = 3L;
        doNothing().when(authenticationService).handleAccessByUserRole(userId, "ADMIN");
        when(patientRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> patientService.getByUserId(userId));

        verify(authenticationService).handleAccessByUserRole(userId, "ADMIN");
        verify(patientRepository).findByUserId(userId);
    }
}
