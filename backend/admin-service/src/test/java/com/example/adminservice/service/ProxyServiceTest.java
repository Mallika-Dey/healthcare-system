package com.example.adminservice.service;

import com.example.adminservice.dto.response.RoomProxyResponseDTO;
import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.entity.Department;
import com.example.adminservice.entity.Room;
import com.example.adminservice.exception.CustomException;
import com.example.adminservice.service.impl.ProxyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProxyServiceTest {
    @Mock
    private DepartmentService departmentService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private ProxyServiceImpl proxyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignRoomToDoctor_Success() {
        String departmentName = "Cardiology";
        Room mockRoom = new Room();
        mockRoom.setId(1);
        mockRoom.setDepartment(Department
                .builder()
                .id(1)
                .deptName(departmentName)
                .capacity(10)
                .build());
        mockRoom.setAvailable(true);

        when(departmentService.getAllRoomsByDepartmentName(departmentName)).thenReturn(List.of(mockRoom));
        doNothing().when(roomService).roomAssignToDoctor(any(Room.class));

        RoomProxyResponseDTO result = proxyService.assignRoomToDoctor(departmentName);

        assertNotNull(result);
        assertEquals(1L, result.getRoomId());
        assertEquals(1L, result.getDeptId());
        verify(departmentService).getAllRoomsByDepartmentName(departmentName);
        verify(roomService).roomAssignToDoctor(any(Room.class));
    }

    @Test
    public void testAssignRoomToDoctor_NoAvailableRoom() {
        String departmentName = "Cardiology";
        Room mockRoom = new Room();
        mockRoom.setAvailable(false);

        when(departmentService.getAllRoomsByDepartmentName(departmentName)).thenReturn(List.of(mockRoom));

        assertThrows(CustomException.class, () -> proxyService.assignRoomToDoctor(departmentName));

        verify(departmentService).getAllRoomsByDepartmentName(departmentName);
        verify(roomService, never()).roomAssignToDoctor(any(Room.class));
    }

    @Test
    public void testGetRoomInfo() {
        Integer roomId = 1;
        RoomResponseDTO mockResponse = new RoomResponseDTO();

        when(roomService.getRoom(roomId)).thenReturn(mockResponse);
        RoomResponseDTO result = proxyService.getRoomInfo(roomId);

        assertNotNull(result);
        verify(roomService).getRoom(roomId);
    }

    @Test
    public void testMakeRoomAvailable() {
        Integer roomId = 1;
        proxyService.makeRoomAvailable(roomId);
        verify(roomService).makeUnavailableRoomToAvailable(roomId);
    }
}
