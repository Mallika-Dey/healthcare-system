package com.example.communityservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull(message = "Doctor id is required")
    private Long doctorId;

    @NotEmpty(message = "Review is required")
    private String reviewText;
}
