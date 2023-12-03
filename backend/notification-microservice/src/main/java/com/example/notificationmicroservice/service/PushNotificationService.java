package com.example.notificationmicroservice.service;

import com.example.notificationmicroservice.dto.response.ProxyResponseDTO;
import com.example.notificationmicroservice.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 * * * * *")
    public void sendNotification() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        log.info("Current date {} and time {}", today, now);

        List<Notification> notifications = notificationService.getNotificationsByDate(today, now);
        for (Notification notification : notifications) {
            log.info("notification is sent to user {}", notification.getUserId());
            String destination = "/user/" + notification.getUserId() + "/notification";
            String message = "Your appointment is today within 2 minutes";

            simpMessagingTemplate.convertAndSend(destination, new ProxyResponseDTO(message));
        }
    }
}
