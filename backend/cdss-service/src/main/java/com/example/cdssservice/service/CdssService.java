package com.example.cdssservice.service;

import com.example.cdssservice.dto.request.ProgressRequestDTO;
import com.example.cdssservice.dto.response.ProgressResponseDTO;
import com.example.cdssservice.dto.response.RecommendationDTO;

public interface CdssService {
    public void addCDSS(ProgressRequestDTO progressRequestDTO);
    public RecommendationDTO getRecommendation(Long authenticatedUserId);
}
