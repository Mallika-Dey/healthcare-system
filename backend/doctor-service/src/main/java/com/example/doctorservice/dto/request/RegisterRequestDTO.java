package com.example.doctorservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "image is required")
    private String image;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Department Name is required")
    private String departmentName;

    @NotEmpty(message = "Medical Name is required")
    private String medicalName;

    @NotEmpty(message = "Degree is required")
    private String degree;

    @NotEmpty(message = "Designation is required")
    private String designation;

    @NotEmpty(message = "Specialization is required")
    private String specialization;

    @NotNull(message = "Year of Experience is required")
    @Min(value = 0, message = "Min experience can't be less than 0")
    private Integer yearOfExperience;

    @NotNull(message = "startTime is required")
    private LocalTime startTime;

    @NotNull(message = "No of daily patient is required")
    @Min(value = 1, message = "No of daily patient can't be less than 1")
    private Integer noOfDailyPatient;
}
