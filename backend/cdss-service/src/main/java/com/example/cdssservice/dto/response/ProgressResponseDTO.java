package com.example.cdssservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponseDTO {
    private double bmi;
    private double bmr;
    private double sugarLevel;
    private LocalDate date;
}
