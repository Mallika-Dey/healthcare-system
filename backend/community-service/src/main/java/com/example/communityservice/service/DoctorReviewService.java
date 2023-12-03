package com.example.communityservice.service;

import com.example.communityservice.dto.request.ReviewDTO;
import com.example.communityservice.dto.response.DoctorReviewResponseDTO;

import java.util.List;

public interface DoctorReviewService {
    public void giveDoctorFeedback(ReviewDTO reviewDTO, Long userId);

    public void deleteDoctorFeedback(Long userId, Long doctorId);

    public List<DoctorReviewResponseDTO> getReviewsOfDoctor(Long doctorId);
}
