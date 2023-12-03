package com.example.communityservice.service.impl;

import com.example.communityservice.dto.request.ReviewDTO;
import com.example.communityservice.dto.response.DoctorReviewResponseDTO;
import com.example.communityservice.dto.response.PatientProxyDTO;
import com.example.communityservice.entity.DoctorReview;
import com.example.communityservice.exception.CustomException;
import com.example.communityservice.feign.DoctorFeignClient;
import com.example.communityservice.feign.PatientFeignClient;
import com.example.communityservice.repository.DoctorReviewRepository;
import com.example.communityservice.service.DoctorReviewService;
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
public class DoctorReviewServiceImpl implements DoctorReviewService {
    private final DoctorReviewRepository doctorReviewRepository;
    private final PatientFeignClient patientFeignClient;
    private final DoctorFeignClient doctorFeignClient;

    @Override
    public void giveDoctorFeedback(ReviewDTO reviewDTO, Long userId) {
        log.info("Attempt to create review ..........");

        if (!doctorFeignClient.isDoctorExistById(reviewDTO.getDoctorId())) {
            log.error("Doctor not exists");
            throw new CustomException(new Date(), "Doctor not exists", HttpStatus.CONFLICT);
        }

        if (doctorReviewRepository.findByUserIdAndDoctorId(userId, reviewDTO.getDoctorId()).isPresent()) {
            log.error("Review already exists");
            throw new CustomException(new Date(), "You have already given review", HttpStatus.CONFLICT);
        }

        DoctorReview doctorReview = new DoctorReview();
        doctorReview.setUserId(userId);
        doctorReview.setDoctorId(reviewDTO.getDoctorId());
        doctorReview.setReviewText(reviewDTO.getReviewText());
        doctorReviewRepository.save(doctorReview);
    }

    @Override
    public void deleteDoctorFeedback(Long userId, Long doctorId) {
        if (!doctorFeignClient.isDoctorExistById(doctorId)) {
            log.error("Doctor not exists");
            throw new CustomException(new Date(), "Doctor not exists", HttpStatus.CONFLICT);
        }

        DoctorReview doctorReview = doctorReviewRepository.findByUserIdAndDoctorId(userId, doctorId)
                .orElseThrow(() -> {
                    log.error("Review not exists");
                    return new CustomException(new Date(), "Review not exists", HttpStatus.NOT_FOUND);
                });

        doctorReviewRepository.delete(doctorReview);
    }

    @Override
    public List<DoctorReviewResponseDTO> getReviewsOfDoctor(Long doctorId) {
        if (!doctorFeignClient.isDoctorExistById(doctorId)) {
            log.error("Doctor not exists");
            throw new CustomException(new Date(), "Doctor not exists", HttpStatus.CONFLICT);
        }

        List<DoctorReview> doctorReviews = doctorReviewRepository.findByDoctorIdOrderByUserIdAsc(doctorId);

        List<Long> userList = doctorReviews.stream().map(DoctorReview::getUserId).toList();
        List<PatientProxyDTO> patientProxyDTOS = patientFeignClient.getByUserId(userList);
        List<DoctorReviewResponseDTO> doctorReviewResponseDTOS = new ArrayList<>();

        for (int i = 0; i < doctorReviews.size(); i++) {
            DoctorReview doctorReview = doctorReviews.get(i);
            PatientProxyDTO patientProxyDTO = patientProxyDTOS.get(i);
            doctorReviewResponseDTOS.add(convertToDoctor(doctorReview, patientProxyDTO));
        }
        return doctorReviewResponseDTOS;
    }

    private DoctorReviewResponseDTO convertToDoctor(DoctorReview doctorReview, PatientProxyDTO patientProxyDTO) {
        return DoctorReviewResponseDTO
                .builder()
                .userId(patientProxyDTO.getUserId())
                .name(patientProxyDTO.getName())
                .imageUrl(patientProxyDTO.getImageUrl())
                .reviewId(doctorReview.getId())
                .reviewText(doctorReview.getReviewText())
                .build();
    }
}
