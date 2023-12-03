package com.example.cdssservice.repository;

import com.example.cdssservice.entity.ProgressHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressHistoryRepository extends JpaRepository<ProgressHistory, Integer> {
    //public List<ProgressHistory> findAllByUserId(Long userId);

    List<ProgressHistory> findTop7ByUserIdOrderByDateDesc(Long userId);
}
