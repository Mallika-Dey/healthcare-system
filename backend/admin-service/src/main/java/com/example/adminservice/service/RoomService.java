package com.example.adminservice.service;

import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.entity.Room;

import java.util.List;

public interface RoomService {
    public List<RoomResponseDTO> getAllRooms();

    public RoomResponseDTO getRoom(Integer roomId);

    public List<RoomResponseDTO> getAllRoomsByDepartmentName(String deptName);

    public void roomAssignToDoctor(Room room);

    public void makeUnavailableRoomToAvailable(Integer roomId);
}
