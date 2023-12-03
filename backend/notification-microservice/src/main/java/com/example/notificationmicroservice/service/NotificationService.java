package com.example.notificationmicroservice.service;

import com.example.notificationmicroservice.dto.request.NotificationDTO;
import com.example.notificationmicroservice.entity.Notification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface NotificationService {
    public void saveNotifications(NotificationDTO notificationDTO);
    public List<Notification> getNotificationsByDate(LocalDate date, LocalTime time);
}
