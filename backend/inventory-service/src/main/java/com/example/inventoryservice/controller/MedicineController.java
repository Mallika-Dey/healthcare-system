package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.request.MedicineRequestDTO;
import com.example.inventoryservice.response.ResponseHandler;
import com.example.inventoryservice.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class MedicineController {
    private final MedicineService medicineService;

    @PostMapping("/create/medicine")
    public ResponseEntity<?> addMedicine(@Valid @RequestBody MedicineRequestDTO medicineRequestDTO) {
        medicineService.addMedicine(medicineRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "Medicine added successfully",
                HttpStatus.OK);
    }

    @PutMapping("/update/medicine")
    public ResponseEntity<?> updateMedicine(@Valid @RequestBody MedicineRequestDTO medicineRequestDTO) {
        medicineService.updateMedicine(medicineRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "Medicine updated successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/medicine/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        medicineService.delete(id);
        return ResponseHandler.generateResponse(new Date(), "Medicine deleted successfully",
                HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse(new Date(), "All Medicines List",
                HttpStatus.OK, medicineService.getAll());
    }

    @GetMapping("/get/medicine/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseHandler.generateResponse(new Date(), "Medicine Details",
                HttpStatus.OK, medicineService.getById(id));
    }
}
