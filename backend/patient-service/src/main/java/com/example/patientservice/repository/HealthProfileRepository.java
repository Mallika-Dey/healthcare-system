package com.example.patientservice.repository;

import com.example.patientservice.entity.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {
    public Optional<HealthProfile> findByUserId(Long userId);
}
