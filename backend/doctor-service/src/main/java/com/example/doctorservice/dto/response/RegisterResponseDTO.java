package com.example.doctorservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {
    private Long userId;
    private String role;
    private String token;
    private int roomId;
    private LocalTime startTime;
    private LocalTime endTime;
}
