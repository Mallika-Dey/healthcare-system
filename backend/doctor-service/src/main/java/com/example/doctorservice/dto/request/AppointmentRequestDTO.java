package com.example.doctorservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AppointmentRequestDTO {
    @NotNull(message = "Doctor user id is required")
    private Long doctorUserId;

    @NotNull(message = "Start Time is required")
    private LocalTime startTime;

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotEmpty(message = "Appointment type is required")
    private String appointmentType;
}
