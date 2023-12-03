package com.example.adminservice.controller;

import com.example.adminservice.response.ResponseHandler;
import com.example.adminservice.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/dashboard")
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/get")
    public ResponseEntity<?> getDashBoardInfo() {
        return ResponseHandler.generateResponse(new Date(), "Dashboard info",
                HttpStatus.OK, dashBoardService.getDashBoardInfo());
    }
}
