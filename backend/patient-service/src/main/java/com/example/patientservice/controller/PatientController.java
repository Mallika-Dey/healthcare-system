package com.example.patientservice.controller;

import com.example.patientservice.response.ResponseHandler;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient/profile")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse(new Date(), "Patient List",
                HttpStatus.OK, patientService.getAll());
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return ResponseHandler.generateResponse(new Date(), "Patient Information",
                HttpStatus.OK, patientService.getByUserId(userId));
    }

    //search users who is interested in blood donate
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String bloodGroup) {
        return ResponseHandler.generateResponse(new Date(), "Patient List",
                HttpStatus.OK, patientService.search(bloodGroup));
    }
}
