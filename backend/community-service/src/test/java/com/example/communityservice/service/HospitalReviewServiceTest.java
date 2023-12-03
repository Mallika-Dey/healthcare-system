package com.example.communityservice.service;

import com.example.communityservice.dto.request.HospitalReviewDTO;
import com.example.communityservice.entity.HospitalReview;
import com.example.communityservice.exception.CustomException;
import com.example.communityservice.repository.HospitalReviewRepository;
import com.example.communityservice.service.impl.HospitalReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalReviewServiceTest {
    @Mock
    private HospitalReviewRepository hospitalReviewRepository;
    @InjectMocks
    private HospitalReviewServiceImpl hospitalReviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGiveHospitalFeedback_AndReviewAlreadyExists_thenThrowException() {
        HospitalReviewDTO hospitalReviewDTO = new HospitalReviewDTO();
        Long userId = 1L;

        when(hospitalReviewRepository.findByUserId(anyLong())).thenReturn(Optional.of(new HospitalReview()));
        assertThrows(CustomException.class, () -> hospitalReviewService.giveHospitalFeedback(hospitalReviewDTO, userId));
    }

    @Test
    public void whenDeleteHospitalFeedback_thenSuccess() {
        Long userId = 1L;
        HospitalReview existingReview = new HospitalReview();
        existingReview.setId(1);

        when(hospitalReviewRepository.findByUserId(anyLong())).thenReturn(Optional.of(existingReview));

        hospitalReviewService.deleteHospitalFeedback(userId);

        verify(hospitalReviewRepository, times(1)).delete(any(HospitalReview.class));
    }

    @Test
    public void whenDeleteHospitalFeedback_AndReviewNotExists_thenThrowException() {
        when(hospitalReviewRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> hospitalReviewService.deleteHospitalFeedback(1L));
    }
}
