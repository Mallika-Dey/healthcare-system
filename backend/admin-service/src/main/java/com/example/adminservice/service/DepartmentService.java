package com.example.adminservice.service;

import com.example.adminservice.dto.request.DepartmentRequestDTO;
import com.example.adminservice.dto.response.DepartmentResponseDTO;
import com.example.adminservice.entity.Room;

import java.util.List;

public interface DepartmentService {
    public void createDepartment(DepartmentRequestDTO departmentDTO);

    public List<Room> getAllRoomsByDepartmentName(String deptName);

    public List<DepartmentResponseDTO> getAllDepartment();

    public void deleteDepartment(String departmentName);

    public boolean departmentIsExist(int departmentId);
}
