package com.example.adminservice.dto.response;

import com.example.adminservice.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDTO {
    private Integer deptId;
    private String deptName;
    private int capacity;
    private List<RoomResponseDTO> rooms;
}
