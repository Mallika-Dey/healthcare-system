package com.example.patientservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Image is required")
    private String imageUrl;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^880\\d{10}$", message = "Mobile number must start with '880' and be followed by 10 digits")
    private String mobileNo;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Blood group is required")
    private String bloodGroup;

    private Boolean interestedInBloodDonate;
}
