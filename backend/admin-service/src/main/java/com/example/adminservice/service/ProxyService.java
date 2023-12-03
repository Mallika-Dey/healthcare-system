package com.example.adminservice.service;

import com.example.adminservice.dto.response.RoomProxyResponseDTO;
import com.example.adminservice.dto.response.RoomResponseDTO;

public interface ProxyService {
    public RoomProxyResponseDTO assignRoomToDoctor(String departmentName);

    public RoomResponseDTO getRoomInfo(Integer roomId);

    public void makeRoomAvailable(Integer roomNo);
}
