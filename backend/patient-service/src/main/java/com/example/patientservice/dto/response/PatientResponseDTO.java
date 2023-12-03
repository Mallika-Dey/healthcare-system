package com.example.patientservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private Long userId;
    private String name;
    private String imageUrl;
    private String address;
    private String mobileNo;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private boolean interestedInBloodDonate;
}
