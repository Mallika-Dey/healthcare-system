package com.example.patientservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressRequestDTO {
    private Long userId;
    private int age;
    private double bmi;
    private double bmr;
    private String gender;
    private String goalType;
    private String activityLevel;
    private String bloodPressure;
    private boolean previousStroke;
    private boolean familyHistoryCardiovascularDisease;
    private boolean highCholesterol;
    private boolean chestPain;
    private double sugarLevel;
    private double fastingBloodGlucoseLevel;
    private String alcoholConsumption;
    private LocalDate date;
}
