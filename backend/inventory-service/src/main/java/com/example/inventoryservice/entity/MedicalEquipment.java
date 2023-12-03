package com.example.inventoryservice.entity;

import com.example.inventoryservice.enums.EquipmentCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MedicalEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentCategory category;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate lastMaintenanceDate;

    @Column(nullable = false)
    private LocalDate nextMaintenanceDate;
}
