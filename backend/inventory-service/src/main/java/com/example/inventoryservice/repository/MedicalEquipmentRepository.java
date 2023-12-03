package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.MedicalEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalEquipmentRepository extends JpaRepository<MedicalEquipment, Integer> {
    Optional<MedicalEquipment> findByName(String name);
}
