package com.example.adminservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardDTO {
    private long patientCount;
    private long totalDonor;
    private long totalDoctor;
    private long totalDepartment;
}
