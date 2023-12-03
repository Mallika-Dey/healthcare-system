package com.example.doctorservice.controller;

import com.example.doctorservice.dto.request.AppointmentRequestDTO;
import com.example.doctorservice.service.impl.AppointmentServiceImpl;
import com.example.doctorservice.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentControllerTest {
    @Mock
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private AppointmentServiceImpl appointmentService;
    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        AppointmentRequestDTO request = new AppointmentRequestDTO();
        Long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);

        ResponseEntity<?> response = appointmentController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService).takeAppointment(request, userId);
    }

    @Test
    public void testGetTodayAppointments() {
        Long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        ResponseEntity<?> response = appointmentController.getTodayAppointments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService).getAllAppointments(userId, LocalDate.now());
    }

    @Test
    public void testGetPatientUpcomingAppointment() {
        Long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        ResponseEntity<?> response = appointmentController.getPatientUpcomingAppointment();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService).getPatientUpcomingAppointments(userId);
    }

    @Test
    public void testGetTodayFreeSlot() {
        Long userId = 1L;
        LocalDate date = LocalDate.now();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        ResponseEntity<?> response = appointmentController.getTodayFreeSlot(userId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService).getAvailableSlots(userId, date);
    }

    @Test
    public void testGetDashboardInfo() {
        Long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        ResponseEntity<?> response = appointmentController.getDashboardInfo();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService).getDashboardData(userId);
    }
}
