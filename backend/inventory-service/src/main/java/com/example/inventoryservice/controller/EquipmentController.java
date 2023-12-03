package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.request.EquipmentRequestDTO;
import com.example.inventoryservice.response.ResponseHandler;
import com.example.inventoryservice.service.MedicalEquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/equipment")
public class EquipmentController {
    private final MedicalEquipmentService medicalEquipmentService;

    @PostMapping("/create")
    public ResponseEntity<?> addEquipment(@Valid @RequestBody EquipmentRequestDTO equipmentRequestDTO) {
        medicalEquipmentService.addEquipment(equipmentRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "Equipment added successfully",
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEquipment(@Valid @RequestBody EquipmentRequestDTO equipmentRequestDTO) {
        medicalEquipmentService.updateEquipment(equipmentRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "Equipment updated successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable int id) {
        medicalEquipmentService.delete(id);
        return ResponseHandler.generateResponse(new Date(), "Equipment deleted successfully",
                HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse(new Date(), "All Equipments List",
                HttpStatus.OK, medicalEquipmentService.getAll());
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseHandler.generateResponse(new Date(), "Equipment Details",
                HttpStatus.OK, medicalEquipmentService.getById(id));
    }
}
