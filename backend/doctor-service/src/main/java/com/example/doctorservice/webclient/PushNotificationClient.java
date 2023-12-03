package com.example.doctorservice.webclient;

import com.example.doctorservice.dto.request.NotificationDTO;
import com.example.doctorservice.dto.response.ProxyResponseDTO;
import reactor.core.publisher.Mono;

public interface PushNotificationClient {
    public Mono<ProxyResponseDTO> saveNotification(NotificationDTO notificationDTO);
}
