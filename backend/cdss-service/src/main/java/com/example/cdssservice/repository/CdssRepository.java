package com.example.cdssservice.repository;

import com.example.cdssservice.entity.Cdss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CdssRepository extends JpaRepository<Cdss, Integer> {
    public Optional<Cdss> findByUserId(Long userId);
}
