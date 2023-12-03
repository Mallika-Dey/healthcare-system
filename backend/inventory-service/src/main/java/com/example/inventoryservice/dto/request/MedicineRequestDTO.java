package com.example.inventoryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDTO {
    private Integer id;

    @NotEmpty(message = "name is required")
    private String name;

    @NotNull(message = "quantity is required")
    private Integer quantity;

    @NotEmpty(message = "description is required")
    private String description;

    @NotEmpty(message = "dosage is required")
    private String dosage;

    @NotEmpty(message = "manufacturer is required")
    private String manufacturer;

    @NotEmpty(message = "batchNumber is required")
    private String batchNumber;

    @NotNull(message = "productionDate is required")
    private LocalDate productionDate;

    @NotNull(message = "expirationDate is required")
    private LocalDate expirationDate;

    @NotEmpty(message = "categoryName is required")
    private String categoryName;

    @NotEmpty(message = "medicineType is required")
    private String medicineType;
}
