package com.example.doctorservice.controller;

import com.example.doctorservice.dto.response.AllDoctorsDTO;
import com.example.doctorservice.dto.response.DoctorInfoDTO;
import com.example.doctorservice.response.ResponseHandler;
import com.example.doctorservice.service.AuthenticationService;
import com.example.doctorservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final AuthenticationService authenticationService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDoctors() {
        List<AllDoctorsDTO> response = doctorService.getAllDoctors();
        return ResponseHandler.generateResponse(new Date(), "Fetch All Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<?> getAllDoctorsByDeptId(@PathVariable int deptId) {
        List<AllDoctorsDTO> response = doctorService.getAllDoctorsByDeptId(deptId);
        return ResponseHandler.generateResponse(new Date(), "Fetch All Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorByUserId(@PathVariable Long doctorId) {
        DoctorInfoDTO response = doctorService.getDoctorByUserId(doctorId);
        return ResponseHandler.generateResponse(new Date(), "Fetch Doctor Data Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/available/{available}")
    public ResponseEntity<?> updateAvailable(@PathVariable boolean available) {
        Long doctorId = authenticationService.getAuthenticatedUserId();
        doctorService.updateAvailability(doctorId, available);
        return ResponseHandler.generateResponse(new Date(), "Updated available status", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String specialization) {
        return ResponseHandler.generateResponse(new Date(), "Doctors data based on searching parameter"
                , HttpStatus.OK, doctorService.searchDoctors(name, department, designation, experience, specialization));
    }
}
