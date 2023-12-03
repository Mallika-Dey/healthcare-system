package com.example.adminservice.service.impl;

import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.entity.Room;
import com.example.adminservice.exception.CustomException;
import com.example.adminservice.repository.RoomRepository;
import com.example.adminservice.service.DepartmentService;
import com.example.adminservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final DepartmentService departmentService;

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public RoomResponseDTO getRoom(Integer roomId) {
        Room room = getRoomById(roomId);
        return mapToDTO(room);
    }

    public Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new CustomException(new Date(),
                "Room doesn't exist", HttpStatus.BAD_REQUEST));
    }

    private RoomResponseDTO mapToDTO(Room room) {
        return RoomResponseDTO
                .builder()
                .roomNumber(room.getRoomNumber())
                .deptName(room.getDepartment().getDeptName())
                .available(room.isAvailable())
                .build();
    }

    @Override
    public List<RoomResponseDTO> getAllRoomsByDepartmentName(String deptName) {
        return departmentService
                .getAllRoomsByDepartmentName(deptName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void roomAssignToDoctor(Room room) {
        room.setAvailable(false);
        roomRepository.save(room);
    }

    @Override
    public void makeUnavailableRoomToAvailable(Integer roomId) {
        Room room = getRoomById(roomId);
        room.setAvailable(true);
        roomRepository.save(room);
    }
}
