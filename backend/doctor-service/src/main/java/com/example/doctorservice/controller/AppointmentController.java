package com.example.doctorservice.controller;

import com.example.doctorservice.dto.request.AppointmentRequestDTO;
import com.example.doctorservice.response.ResponseHandler;
import com.example.doctorservice.service.AppointmentService;
import com.example.doctorservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctor/appointment")
public class AppointmentController {
    private final AuthenticationService authenticationService;
    private final AppointmentService appointmentService;

    @PostMapping("/take")
    public ResponseEntity<?> register(@Valid @RequestBody AppointmentRequestDTO request) {
        Long userId = authenticationService.getAuthenticatedUserId();
        appointmentService.takeAppointment(request, userId);
        return ResponseHandler.generateResponse(new Date(), "Appointment Successful", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTodayAppointments() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return ResponseHandler.generateResponse(new Date(), "Today's appointments",
                HttpStatus.OK, appointmentService.getAllAppointments(userId, LocalDate.now()));
    }

    @GetMapping("/get/patient/upcoming")
    public ResponseEntity<?> getPatientUpcomingAppointment() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return ResponseHandler.generateResponse(new Date(), "Upcoming appointments",
                HttpStatus.OK, appointmentService.getPatientUpcomingAppointments(userId));
    }

    @GetMapping("/doctor/{userId}/freeSlot/{date}")
    public ResponseEntity<?> getTodayFreeSlot(@PathVariable Long userId, @PathVariable LocalDate date) {
        return ResponseHandler.generateResponse(new Date(), "Available slots",
                HttpStatus.OK, appointmentService.getAvailableSlots(userId, date));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardInfo() {
        long userId = authenticationService.getAuthenticatedUserId();
        return ResponseHandler.generateResponse(new Date(), "Dashboard",
                HttpStatus.OK, appointmentService.getDashboardData(userId));
    }
}
