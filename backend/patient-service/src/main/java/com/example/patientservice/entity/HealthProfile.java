package com.example.patientservice.entity;

import com.example.patientservice.enums.ActivityLevel;
import com.example.patientservice.enums.Gender;
import com.example.patientservice.enums.GoalType;
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
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;

    private int age;
    private double weight;
    private double height;
    private double bmi;
    private double bmr;

    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "healthProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private PhysicalHealth physicalHealth;

}
