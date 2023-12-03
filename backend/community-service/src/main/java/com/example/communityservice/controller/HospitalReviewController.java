package com.example.communityservice.controller;

import com.example.communityservice.dto.request.HospitalReviewDTO;
import com.example.communityservice.response.ResponseHandler;
import com.example.communityservice.service.AuthenticationService;
import com.example.communityservice.service.HospitalReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class HospitalReviewController {
    private final HospitalReviewService hospitalReviewService;
    private final AuthenticationService authenticationService;

    @PostMapping("/review/hospital")
    public ResponseEntity<?> giveHospitalReview(@Valid @RequestBody HospitalReviewDTO hospitalReviewDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        hospitalReviewService.giveHospitalFeedback(hospitalReviewDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Review Successfully added", HttpStatus.CREATED);
    }

    @DeleteMapping("/review/hospital")
    public ResponseEntity<?> deleteHospitalReview() {
        Long userId = authenticationService.getAuthenticatedUserId();
        hospitalReviewService.deleteHospitalFeedback(userId);
        return ResponseHandler.generateResponse(new Date(), "Review deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/get/review/hospital")
    public ResponseEntity<?> getReviewsOfHospital() {
        return ResponseHandler.generateResponse(new Date(), "Reviews fetched successfully",
                HttpStatus.OK, hospitalReviewService.getReviewsOfHospital());
    }
}
