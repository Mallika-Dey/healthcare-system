package com.example.cdssservice.service.impl;

import com.example.cdssservice.dto.request.ProgressRequestDTO;
import com.example.cdssservice.dto.response.ProgressResponseDTO;
import com.example.cdssservice.entity.ProgressHistory;
import com.example.cdssservice.repository.ProgressHistoryRepository;
import com.example.cdssservice.service.ProgressHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressHistoryServiceImpl implements ProgressHistoryService {
    private final ProgressHistoryRepository progressHistoryRepository;

    @Override
    public void addHealthData(ProgressRequestDTO progressRequestDTO) {
        //builder design pattern
        progressHistoryRepository.save(new ProgressHistory
                .Builder()
                .userId(progressRequestDTO.getUserId())
                .bmi(progressRequestDTO.getBmi())
                .bmr(progressRequestDTO.getBmr())
                .sugarLevel(progressRequestDTO.getSugarLevel())
                .date(progressRequestDTO.getDate())
                .build());
    }

    @Override
    public List<ProgressResponseDTO> getHealthData(Long userId) {
        return progressHistoryRepository.findTop7ByUserIdOrderByDateDesc(userId)
                .stream()
                .map(this::mapToDTO).toList();
    }

    private ProgressResponseDTO mapToDTO(ProgressHistory progressHistory) {
        return ProgressResponseDTO
                .builder()
                .bmi(progressHistory.getBmi())
                .bmr(progressHistory.getBmr())
                .sugarLevel(progressHistory.getSugarLevel())
                .date(progressHistory.getDate())
                .build();
    }
}
