package com.example.inventoryservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRequestDTO {
    private Integer id;

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "name is required")
    private String category;

    @NotEmpty(message = "manufacturer is required")
    private String manufacturer;

    @NotEmpty(message = "serial number is required")
    private String serialNumber;

    @NotNull(message = "quantity is required")
    private Integer quantity;

    @NotNull(message = "last maintenance date is required")
    private LocalDate lastMaintenanceDate;

    @NotNull(message = "next maintenance date is required")
    private LocalDate nextMaintenanceDate;
}
