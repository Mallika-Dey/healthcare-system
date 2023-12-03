package com.example.communityservice.service;

import com.example.communityservice.dto.request.HospitalReviewDTO;
import com.example.communityservice.dto.response.HospitalReviewResponseDTO;

import java.util.List;

public interface HospitalReviewService {
    public void giveHospitalFeedback(HospitalReviewDTO hospitalReviewDTO, Long userId);

    public void deleteHospitalFeedback(Long userId);
    public List<HospitalReviewResponseDTO> getReviewsOfHospital();

}
