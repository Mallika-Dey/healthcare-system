package com.example.communityservice.controller;

import com.example.communityservice.dto.request.ReviewDTO;
import com.example.communityservice.response.ResponseHandler;
import com.example.communityservice.service.AuthenticationService;
import com.example.communityservice.service.DoctorReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class DoctorReviewController {
    private final DoctorReviewService doctorReviewService;
    private final AuthenticationService authenticationService;

    @PostMapping("/review/doctor")
    public ResponseEntity<?> giveDoctorReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        doctorReviewService.giveDoctorFeedback(reviewDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Review Successfully added", HttpStatus.CREATED);
    }

    @DeleteMapping("/review/doctor/{doctorId}")
    public ResponseEntity<?> deleteDoctorReview(@PathVariable Long doctorId) {
        Long userId = authenticationService.getAuthenticatedUserId();
        doctorReviewService.deleteDoctorFeedback(userId, doctorId);
        return ResponseHandler.generateResponse(new Date(), "Review deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/get/review/doctor/{doctorId}")
    public ResponseEntity<?> getReviewsOfDoctor(@PathVariable Long doctorId) {
        return ResponseHandler.generateResponse(new Date(), "Reviews fetched successfully",
                HttpStatus.OK, doctorReviewService.getReviewsOfDoctor(doctorId));
    }
}
