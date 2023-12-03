package com.example.communityservice.service;

import com.example.communityservice.dto.request.ReviewDTO;
import com.example.communityservice.entity.DoctorReview;
import com.example.communityservice.exception.CustomException;
import com.example.communityservice.feign.DoctorFeignClient;
import com.example.communityservice.feign.PatientFeignClient;
import com.example.communityservice.repository.DoctorReviewRepository;
import com.example.communityservice.service.impl.DoctorReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DoctorReviewServiceTest {
    @Mock
    private DoctorReviewRepository doctorReviewRepository;
    @Mock
    private PatientFeignClient patientFeignClient;
    @Mock
    private DoctorFeignClient doctorFeignClient;
    @InjectMocks
    private DoctorReviewServiceImpl doctorReviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidData_whenGiveDoctorFeedback_thenSuccess() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDoctorId(1L);
        reviewDTO.setReviewText("Great Doctor!");

        when(doctorFeignClient.isDoctorExistById(anyLong())).thenReturn(true);
        when(doctorReviewRepository.findByUserIdAndDoctorId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        doctorReviewService.giveDoctorFeedback(reviewDTO, 1L);

        verify(doctorReviewRepository, times(1)).save(any(DoctorReview.class));
    }

    @Test
    public void givenDoctorDoesNotExist_whenGiveDoctorFeedback_thenThrowCustomException() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDoctorId(1L);
        reviewDTO.setReviewText("Excellent service!");

        when(doctorFeignClient.isDoctorExistById(anyLong())).thenReturn(false);
        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> doctorReviewService.giveDoctorFeedback(reviewDTO, 1L)
        );

        assertEquals("Doctor not exists", thrownException.getMessage());
        assertEquals(HttpStatus.CONFLICT, thrownException.getStatus());
    }
}
