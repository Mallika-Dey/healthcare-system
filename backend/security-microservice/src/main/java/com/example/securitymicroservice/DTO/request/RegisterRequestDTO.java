package com.example.securitymicroservice.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotEmpty(message = "Name is required")
    @Size(min = 5, message = "Name should have at least 5 character")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be null or empty")
    private String password;

    @NotEmpty(message = "Role should not be null or empty")
    private String role;

}