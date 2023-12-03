package com.example.adminservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.entity.Department;
import com.example.adminservice.entity.Room;
import com.example.adminservice.exception.CustomException;
import com.example.adminservice.repository.RoomRepository;
import com.example.adminservice.service.impl.DepartmentServiceImpl;
import com.example.adminservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private DepartmentServiceImpl departmentService;
    @InjectMocks
    private RoomServiceImpl roomService;
    private Room room;

    @BeforeEach
    public void setUp() {
        room = Room
                .builder()
                .roomNumber(1)
                .department(new Department())
                .roomNumber(10)
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());
        List<RoomResponseDTO> result = roomService.getAllRooms();

        assertTrue(result.isEmpty());
        verify(roomRepository).findAll();
    }

    @Test
    public void testGetRoom() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        RoomResponseDTO result = roomService.getRoom(1);

        assertNotNull(result);
        verify(roomRepository).findById(1);
    }

    @Test
    public void testGetRoomById_NotFound() {
        Integer roomId = 1;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> roomService.getRoomById(roomId));
    }
}