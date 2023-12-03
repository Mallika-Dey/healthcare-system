package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.request.EquipmentRequestDTO;
import com.example.inventoryservice.dto.response.EquipmentResponseDTO;
import com.example.inventoryservice.entity.MedicalEquipment;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.repository.MedicalEquipmentRepository;
import com.example.inventoryservice.service.MedicalEquipmentService;
import com.example.inventoryservice.utils.EnumValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalEquipmentServiceImpl implements MedicalEquipmentService {
    private final MedicalEquipmentRepository medicalEquipmentRepository;

    @Override
    public void addEquipment(EquipmentRequestDTO equipmentRequestDTO) {
        if (equipmentRequestDTO.getLastMaintenanceDate().isAfter(equipmentRequestDTO.getNextMaintenanceDate())) {
            throw new CustomException(new Date(), "Last maintenance date should be before" +
                    " next maintenance", HttpStatus.BAD_REQUEST);
        }
        medicalEquipmentRepository.save(convertToEquipment(equipmentRequestDTO));
    }

    @Override
    public void updateEquipment(EquipmentRequestDTO equipmentRequestDTO) {
        if (equipmentRequestDTO.getLastMaintenanceDate().isAfter(equipmentRequestDTO.getNextMaintenanceDate())) {
            throw new CustomException(new Date(), "Last maintenance date should be before" +
                    " next maintenance", HttpStatus.BAD_REQUEST);
        }

        Integer equipmentId = findEquipment(equipmentRequestDTO.getId()).getId();
        MedicalEquipment equipment = convertToEquipment(equipmentRequestDTO);
        equipment.setId(equipmentId);
        medicalEquipmentRepository.save(equipment);
    }

    @Override
    public void delete(int id) {
        MedicalEquipment medicalEquipment = findEquipment(id);
        medicalEquipmentRepository.delete(medicalEquipment);
    }

    @Override
    public List<EquipmentResponseDTO> getAll() {
        return medicalEquipmentRepository
                .findAll()
                .stream()
                .map(this::mapToDTO).toList();
    }

    @Override
    public EquipmentResponseDTO getById(int id) {
        MedicalEquipment medicalEquipment = medicalEquipmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(new Date(), "Equipment not found", HttpStatus.OK));
        return mapToDTO(medicalEquipment);
    }

    private MedicalEquipment findEquipment(Integer id) {
        return medicalEquipmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(new Date(), "equipment not exists", HttpStatus.NOT_FOUND));
    }

    private EquipmentResponseDTO mapToDTO(MedicalEquipment equipment) {
        return EquipmentResponseDTO
                .builder()
                .name(equipment.getName())
                .category(equipment.getCategory().toString())
                .manufacturer(equipment.getManufacturer())
                .serialNumber(equipment.getSerialNumber())
                .quantity(equipment.getQuantity())
                .lastMaintenanceDate(equipment.getLastMaintenanceDate())
                .nextMaintenanceDate(equipment.getNextMaintenanceDate())
                .build();
    }

    private MedicalEquipment convertToEquipment(EquipmentRequestDTO equipmentRequestDTO) {
        return MedicalEquipment.builder()
                .name(equipmentRequestDTO.getName())
                .category(EnumValidator.parseEquipmentCategory(equipmentRequestDTO.getCategory()))
                .manufacturer(equipmentRequestDTO.getManufacturer())
                .serialNumber(equipmentRequestDTO.getSerialNumber())
                .quantity(equipmentRequestDTO.getQuantity())
                .lastMaintenanceDate(equipmentRequestDTO.getLastMaintenanceDate())
                .nextMaintenanceDate(equipmentRequestDTO.getNextMaintenanceDate())
                .build();
    }
}
