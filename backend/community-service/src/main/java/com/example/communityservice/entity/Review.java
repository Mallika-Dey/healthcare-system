package com.example.communityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Review {
    private Long userId;

    @Column(nullable = false)
    private String reviewText;
}
