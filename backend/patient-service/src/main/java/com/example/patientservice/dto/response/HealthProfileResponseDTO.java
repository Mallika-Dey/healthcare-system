package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthProfileResponseDTO {
    private int age;
    private double weight;
    private double height;
    private double bmi;
    private double bmr;
    private String bloodGroup;
    private String goalType;
    private String activityLevel;
    private String gender;
    private String bloodPressure;
    private boolean smoke;
    private boolean sinusitis;
    private boolean previousStroke;
    private boolean familyHistoryCardiovascularDisease;
    private boolean highCholesterol;
    private boolean chestPain;
    private double sugarLevel;
    private double fastingBloodGlucoseLevel;
    private String alcoholConsumption;
    private String thirstLevel;
}
