package com.example.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponseDTO {
    private String name;

    private String category;

    private String manufacturer;

    private String serialNumber;

    private int quantity;

    private LocalDate lastMaintenanceDate;

    private LocalDate nextMaintenanceDate;
}
