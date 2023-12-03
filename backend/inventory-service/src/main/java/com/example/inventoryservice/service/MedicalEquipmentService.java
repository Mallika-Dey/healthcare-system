package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.request.EquipmentRequestDTO;
import com.example.inventoryservice.dto.response.EquipmentResponseDTO;

import java.util.List;

public interface MedicalEquipmentService {
    public void addEquipment(EquipmentRequestDTO equipmentRequestDTO);

    public void updateEquipment(EquipmentRequestDTO equipmentRequestDTO);

    public void delete(int id);

    public List<EquipmentResponseDTO> getAll();

    public EquipmentResponseDTO getById(int id);
}
