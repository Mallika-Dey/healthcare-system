package com.example.inventoryservice.dto.response;

import com.example.inventoryservice.enums.MedicineCategory;
import com.example.inventoryservice.enums.MedicineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponseDTO {
    private String name;
    private int quantity;
    private String description;
    private String dosage;
    private String manufacturer;
    private String batchNumber;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private String categoryName;
    private String medicineType;
}
