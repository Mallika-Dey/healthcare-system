package com.example.adminservice.controller;

import com.example.adminservice.dto.request.DepartmentRequestDTO;
import com.example.adminservice.dto.response.DepartmentResponseDTO;
import com.example.adminservice.response.ResponseHandler;
import com.example.adminservice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/createDept")
    public ResponseEntity<?> createDeptWithRooms(
            @Valid @RequestBody DepartmentRequestDTO departmentDTO) {
        departmentService.createDepartment(departmentDTO);
        return ResponseHandler.generateResponse(new Date(), "Department created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getAllDept")
    public ResponseEntity<Object> getAllDeptDetails() {
        List<DepartmentResponseDTO> department = departmentService.getAllDepartment();
        return ResponseHandler.generateResponse(new Date(), "All departments", HttpStatus.OK, department);
    }

    @DeleteMapping("/deleteDept/{departmentName}")
    public ResponseEntity<?> deleteDepartment(@Valid @PathVariable String departmentName) {
        departmentService.deleteDepartment(departmentName);
        return ResponseHandler.generateResponse(new Date(), "Delete Department Successfully", HttpStatus.OK);
    }
}
