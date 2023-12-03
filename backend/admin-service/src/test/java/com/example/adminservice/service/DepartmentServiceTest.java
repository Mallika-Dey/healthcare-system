package com.example.adminservice.service;

import com.example.adminservice.dto.request.DepartmentRequestDTO;
import com.example.adminservice.entity.Department;
import com.example.adminservice.repository.DepartmentRepository;
import com.example.adminservice.repository.RoomRepository;
import com.example.adminservice.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private RoomRepository roomRepository;
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentService = new DepartmentServiceImpl(departmentRepository, roomRepository);
    }

    @Test
    void createDepartment_success() {
        DepartmentRequestDTO departmentDTO = new DepartmentRequestDTO("Cardiology", 10);
        when(departmentRepository.findByDeptName(anyString())).thenReturn(Optional.empty());

        departmentService.createDepartment(departmentDTO);

        verify(departmentRepository).save(any(Department.class));
        verify(roomRepository).saveAll(anyList());
    }
}
