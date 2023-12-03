package com.example.notificationmicroservice.repository;

import com.example.notificationmicroservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    public Optional<Notification> findByUserIdAndDate(Long userId, LocalDate date);

    public List<Notification> findAllByDateAndStartTime(LocalDate date, LocalTime time);
}
