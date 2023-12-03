package com.example.notificationmicroservice.controller;

import com.example.notificationmicroservice.dto.request.NotificationDTO;
import com.example.notificationmicroservice.dto.response.ProxyResponseDTO;
import com.example.notificationmicroservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/notification/proxy")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/save")
    public Mono<ProxyResponseDTO> saveNotification(@RequestBody NotificationDTO notificationDTO) {
        return Mono.fromRunnable(() -> notificationService.saveNotifications(notificationDTO))
                .thenReturn(new ProxyResponseDTO("notification for user is saved"));
    }
}
