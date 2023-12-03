package com.example.adminservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private int roomNumber;
    private String deptName;
    private boolean available;
}
