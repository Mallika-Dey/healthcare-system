package com.example.doctorservice.dto.response;

import com.example.doctorservice.enums.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDTO {
    private Long patientId;
    private String name;
    private String imageUrl;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appointmentDate;
    private String appointmentType;
}
