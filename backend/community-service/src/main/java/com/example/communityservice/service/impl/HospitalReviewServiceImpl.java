package com.example.communityservice.service.impl;

import com.example.communityservice.dto.request.HospitalReviewDTO;
import com.example.communityservice.dto.response.HospitalReviewResponseDTO;
import com.example.communityservice.dto.response.PatientProxyDTO;
import com.example.communityservice.entity.HospitalReview;
import com.example.communityservice.exception.CustomException;
import com.example.communityservice.feign.PatientFeignClient;
import com.example.communityservice.repository.HospitalReviewRepository;
import com.example.communityservice.service.HospitalReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HospitalReviewServiceImpl implements HospitalReviewService {
    private final HospitalReviewRepository hospitalReviewRepository;
    private final PatientFeignClient patientFeignClient;

    @Override
    public void giveHospitalFeedback(HospitalReviewDTO hospitalReviewDTO, Long userId) {
        log.info("Attempt to create review ..........");
        if (hospitalReviewRepository.findByUserId(userId).isPresent()) {
            log.error("Review already exists");
            throw new CustomException(new Date(), "Review already exists", HttpStatus.CONFLICT);
        }

        HospitalReview hospitalReview = new HospitalReview();
        hospitalReview.setUserId(userId);
        hospitalReview.setRating(hospitalReviewDTO.getRating());
        hospitalReview.setReviewText(hospitalReviewDTO.getReviewText());
        hospitalReviewRepository.save(hospitalReview);
    }

    @Override
    public void deleteHospitalFeedback(Long userId) {
        HospitalReview hospitalReview = hospitalReviewRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Review not exists");
                    return new CustomException(new Date(), "Review not exists", HttpStatus.NOT_FOUND);
                });

        hospitalReviewRepository.delete(hospitalReview);
    }

    @Override
    public List<HospitalReviewResponseDTO> getReviewsOfHospital() {
        List<HospitalReview> hospitalReviews = hospitalReviewRepository.findAllByOrderByUserIdAsc();
        List<Long> userList = hospitalReviews.stream().map(HospitalReview::getUserId).toList();
        List<PatientProxyDTO> patientProxyDTOS = patientFeignClient.getByUserId(userList);
        List<HospitalReviewResponseDTO> hospitalReviewResponseDTOS = new ArrayList<>();

        for (int i = 0; i < hospitalReviews.size(); i++) {
            HospitalReview hospitalReview = hospitalReviews.get(i);
            PatientProxyDTO patientProxyDTO = patientProxyDTOS.get(i);
            hospitalReviewResponseDTOS.add(convertToHospitalDTO(hospitalReview, patientProxyDTO));
        }
        return hospitalReviewResponseDTOS;
    }

    private HospitalReviewResponseDTO convertToHospitalDTO(HospitalReview hospitalReview, PatientProxyDTO patientProxyDTO) {
        return HospitalReviewResponseDTO
                .builder()
                .userId(patientProxyDTO.getUserId())
                .name(patientProxyDTO.getName())
                .imageUrl(patientProxyDTO.getImageUrl())
                .reviewId(hospitalReview.getId())
                .reviewText(hospitalReview.getReviewText())
                .rating(hospitalReview.getRating())
                .build();
    }
}
