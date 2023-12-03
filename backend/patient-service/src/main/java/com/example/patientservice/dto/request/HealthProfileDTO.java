package com.example.patientservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthProfileDTO {
    @Positive(message = "Age must be a positive number")
    private int age;

    @Positive(message = "Weight must be a positive number")
    private double weight;

    @Positive(message = "Height must be a positive number")
    private double height;

    @NotEmpty(message = "Goal type is required")
    private String goalType;

    @NotEmpty(message = "Activity level is required")
    private String activityLevel;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Blood pressure is required")
    private String bloodPressure;

    @NotNull(message = "Smoke information is required")
    private Boolean smoke;

    @NotNull(message = "Sinusitis information is required")
    private Boolean sinusitis;

    @NotNull(message = "Previous stoke information is required")
    private Boolean previousStroke;

    @NotNull(message = "Family history cardio vascular disease is required")
    private Boolean familyHistoryCardiovascularDisease;

    @NotNull(message = "High cholesterol information is required")
    private Boolean highCholesterol;

    @NotNull(message = "Chest pain situation is required")
    private Boolean chestPain;

    @Positive(message = "Sugar level must be a positive number")
    private double sugarLevel;

    @Positive(message = "Fasting blood glucose level must be a positive number")
    private double fastingBloodGlucoseLevel;

    @NotEmpty(message = "Alcohol consumption is required")
    private String alcoholConsumption;

    @NotEmpty(message = "Thirst level is required")
    private String thirstLevel;
}
