package com.example.doctorservice.entity;

import com.example.doctorservice.enums.Degree;
import com.example.doctorservice.enums.Designation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;

    private int deptId;
    private int roomId;

    private String name;
    private String image;
    private String departmentName;
    private String medicalName;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private String specialization;
    private int yearOfExperience;
    private LocalTime startTime;
    private LocalTime endTime;
    private int noOfDailyPatient;
    private boolean available = false;
}
