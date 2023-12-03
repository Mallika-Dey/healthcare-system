package com.example.communityservice.repository;

import com.example.communityservice.entity.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Integer> {
    public Optional<DoctorReview> findByUserId(Long userId);

    public Optional<DoctorReview> findByUserIdAndDoctorId(Long userId, Long doctorId);

    public List<DoctorReview> findByDoctorIdOrderByUserIdAsc(Long doctorId);
}
