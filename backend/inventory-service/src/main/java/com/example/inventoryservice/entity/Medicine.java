package com.example.inventoryservice.entity;

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
@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private LocalDate productionDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private MedicineCategory categoryName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;
}
