package com.example.notificationmicroservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long patientId;
    private Long doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime appointmentTime;
}
