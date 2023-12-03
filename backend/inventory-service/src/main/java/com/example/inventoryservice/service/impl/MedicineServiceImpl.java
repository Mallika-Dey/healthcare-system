package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.request.MedicineRequestDTO;
import com.example.inventoryservice.dto.response.MedicineResponseDTO;
import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.repository.MedicineRepository;
import com.example.inventoryservice.service.MedicineService;
import com.example.inventoryservice.utils.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    @Override
    public void addMedicine(MedicineRequestDTO medicineRequestDTO) {
        validateDate(medicineRequestDTO);
        Medicine medicine = convertToMedicine(medicineRequestDTO);
        medicineRepository.save(medicine);
    }

    @Override
    public void updateMedicine(MedicineRequestDTO medicineRequestDTO) {
        validateDate(medicineRequestDTO);
        Integer medicineId = findMedicine(medicineRequestDTO.getId()).getId();
        Medicine medicine = convertToMedicine(medicineRequestDTO);
        medicine.setId(medicineId);
        medicineRepository.save(medicine);
    }

    private void validateDate(MedicineRequestDTO medicineRequestDTO) {
        if (medicineRequestDTO.getProductionDate().isAfter(LocalDate.now())) {
            throw new CustomException(new Date(), "Production date should be past"
                    , HttpStatus.BAD_REQUEST);
        }

        if (medicineRequestDTO.getProductionDate().isAfter(medicineRequestDTO.getExpirationDate())) {
            throw new CustomException(new Date(), "Production date should be before" +
                    " Expiration date", HttpStatus.BAD_REQUEST);
        }
    }

    private Medicine convertToMedicine(MedicineRequestDTO medicineRequestDTO) {
        return Medicine.builder()
                .name(medicineRequestDTO.getName())
                .quantity(medicineRequestDTO.getQuantity())
                .description(medicineRequestDTO.getDescription())
                .dosage(medicineRequestDTO.getDosage())
                .manufacturer(medicineRequestDTO.getManufacturer())
                .batchNumber(medicineRequestDTO.getBatchNumber())
                .productionDate(medicineRequestDTO.getProductionDate())
                .expirationDate(medicineRequestDTO.getExpirationDate())
                .categoryName(EnumValidator.parseMedicineCategory(medicineRequestDTO.getCategoryName()))
                .medicineType(EnumValidator.parseMedicineType(medicineRequestDTO.getMedicineType()))
                .build();
    }

    private Medicine findMedicine(Integer id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new CustomException(new Date(), "Medicine not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(int id) {
        Medicine medicine = findMedicine(id);
        medicineRepository.delete(medicine);
    }

    @Override
    public List<MedicineResponseDTO> getAll() {
        return medicineRepository.findAll().stream().map(this::mapToMedicineResponse).toList();
    }

    private MedicineResponseDTO mapToMedicineResponse(Medicine medicine) {
        return MedicineResponseDTO
                .builder()
                .name(medicine.getName())
                .quantity(medicine.getQuantity())
                .description(medicine.getDescription())
                .dosage(medicine.getDosage())
                .manufacturer(medicine.getManufacturer())
                .batchNumber(medicine.getBatchNumber())
                .productionDate(medicine.getProductionDate())
                .expirationDate(medicine.getExpirationDate())
                .categoryName(medicine.getCategoryName().toString())
                .medicineType(medicine.getMedicineType().toString())
                .build();
    }

    @Override
    public MedicineResponseDTO getById(int id) {
        return mapToMedicineResponse(findMedicine(id));
    }
}
