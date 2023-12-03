package com.example.cdssservice.service;

import com.example.cdssservice.dto.request.ProgressRequestDTO;
import com.example.cdssservice.dto.response.ProgressResponseDTO;

import java.util.List;

public interface ProgressHistoryService {
    public void addHealthData(ProgressRequestDTO progressRequestDTO);

    public List<ProgressResponseDTO> getHealthData(Long userId);
}
