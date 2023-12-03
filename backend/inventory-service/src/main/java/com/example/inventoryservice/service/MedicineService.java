package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.request.MedicineRequestDTO;
import com.example.inventoryservice.dto.response.MedicineResponseDTO;

import java.util.List;

public interface MedicineService {
    public void addMedicine(MedicineRequestDTO medicineRequestDTO);

    public void updateMedicine(MedicineRequestDTO medicineRequestDTO);

    public void delete(int id);

    public List<MedicineResponseDTO> getAll();

    public MedicineResponseDTO getById(int id);
}
