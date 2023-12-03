package com.example.adminservice.controller;

import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.response.ResponseHandler;
import com.example.adminservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRooms() {
        List<RoomResponseDTO> response = roomService.getAllRooms();
        return ResponseHandler.generateResponse(new Date(), "Fetch All Rooms Successfully",
                HttpStatus.OK, response);
    }

    @GetMapping("/getByDepartment/{deptName}")
    public ResponseEntity<?> getRoomsByDepartment(@PathVariable String deptName) {
        List<RoomResponseDTO> response = roomService.getAllRoomsByDepartmentName(deptName);
        return ResponseHandler.generateResponse(new Date(), "Fetch All Rooms Successfully",
                HttpStatus.OK, response);
    }
}
