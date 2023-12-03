package com.example.doctorservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfoDTO {
    private long userId;
    private long roomNumber;
    private String name;
    private String image;
    private String departmentName;
    private String medicalName;
    private String degree;
    private String designation;
    private String specialization;
    private int yearOfExperience;
    private LocalTime startTime;
    private LocalTime endTime;
    private int noOfDailyPatient;
    private boolean available;
}
