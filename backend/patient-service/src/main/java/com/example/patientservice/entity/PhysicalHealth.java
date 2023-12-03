package com.example.patientservice.entity;

import com.example.patientservice.enums.AlcoholConsumption;
import com.example.patientservice.enums.BloodPressure;
import com.example.patientservice.enums.ThirstLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalHealth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean smoke;
    private boolean sinusitis;
    private boolean previousStroke;
    private boolean familyHistoryCardiovascularDisease;
    private boolean highCholesterol;
    private boolean chestPain;
    private double sugarLevel;
    private double fastingBloodGlucoseLevel;

    @Enumerated(EnumType.STRING)
    private BloodPressure bloodPressure;

    @Enumerated(EnumType.STRING)
    private AlcoholConsumption alcoholConsumption;

    @Enumerated(EnumType.STRING)
    private ThirstLevel thirstLevel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private HealthProfile healthProfile;

}
