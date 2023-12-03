package com.example.patientservice.controller;

import com.example.patientservice.dto.request.HealthProfileDTO;
import com.example.patientservice.response.ResponseHandler;
import com.example.patientservice.service.AuthenticationService;
import com.example.patientservice.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/health")
public class HealthProfileController {
    private final HealthProfileService healthProfileService;
    private final AuthenticationService authenticationService;

    @PostMapping("/createHealth")
    public ResponseEntity<?> createHealthProfile(@Valid @RequestBody HealthProfileDTO healthProfileDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        healthProfileService.createHealthProfile(healthProfileDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Successfully create profile", HttpStatus.OK);
    }

    @PutMapping("/updateHealth")
    public ResponseEntity<?> updateHealthProfile(@Valid @RequestBody HealthProfileDTO healthProfileDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        healthProfileService.updateHealthProfile(healthProfileDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Successfully updated profile", HttpStatus.OK);
    }

    @GetMapping("/getByUserId/health-profile")
    public ResponseEntity<?> getHealthProfileByUserId() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return ResponseHandler.generateResponse(new Date(), "Patient Data",
                HttpStatus.OK, healthProfileService.getUserHealthProfile(userId));
    }
}
