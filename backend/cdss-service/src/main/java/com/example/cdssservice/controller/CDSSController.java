package com.example.cdssservice.controller;

import com.example.cdssservice.dto.request.ConsultRequestDTO;
import com.example.cdssservice.dto.response.ConsultResponseDTO;
import com.example.cdssservice.response.ResponseHandler;
import com.example.cdssservice.service.AuthenticationService;
import com.example.cdssservice.service.CdssService;
import com.example.cdssservice.service.ConsultService;
import com.example.cdssservice.service.ProgressHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cdss")
public class CDSSController {
    private final ConsultService consultService;
    private final ProgressHistoryService progressHistoryService;
    private final CdssService cdssService;
    private final AuthenticationService authenticationService;

    @GetMapping("/start")
    public ResponseEntity<?> startConsultation() {
        consultService.resetConversation();
        return ResponseEntity.ok(consultService.getNextStep(null));
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respondToQuestion(@RequestBody ConsultRequestDTO response) {
        ConsultResponseDTO nextStep = consultService.getNextStep(response.getAnswer());
        return ResponseEntity.ok(nextStep);
    }

    @GetMapping("/get/patient/progress")
    public ResponseEntity<?> getHealthProgress() {
        return ResponseHandler.generateResponse(new Date(), "Health History"
                , HttpStatus.OK, progressHistoryService.getHealthData(
                        authenticationService.getAuthenticatedUserId()));
    }

    @GetMapping("/get/patient/recommendation")
    public ResponseEntity<?> getHealthRecommendation() {
        return ResponseHandler.generateResponse(new Date(), "Recommendation Data"
                , HttpStatus.OK, cdssService.getRecommendation(
                        authenticationService.getAuthenticatedUserId()));
    }
}
