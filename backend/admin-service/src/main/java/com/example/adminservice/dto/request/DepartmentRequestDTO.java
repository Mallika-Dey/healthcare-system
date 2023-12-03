package com.example.adminservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDTO {
    @NotEmpty(message = "Department name is required")
    private String deptName;

    @NotNull(message = "Capacity is required")
    @Min(value = 0, message = "Capacity should not be negative")
    private Integer capacity;
}
