package com.example.communityservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorReviewResponseDTO {
    private Long userId;
    private String name;
    private String imageUrl;
    private Integer reviewId;
    private String reviewText;
}
