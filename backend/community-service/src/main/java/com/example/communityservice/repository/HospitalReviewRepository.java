package com.example.communityservice.repository;

import com.example.communityservice.entity.HospitalReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalReviewRepository extends JpaRepository<HospitalReview, Integer> {
    public Optional<HospitalReview> findByUserId(Long userId);

    public List<HospitalReview> findAllByOrderByUserIdAsc();
}
