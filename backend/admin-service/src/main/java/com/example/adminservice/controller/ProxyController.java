package com.example.adminservice.controller;

import com.example.adminservice.dto.request.RoomUnavailableRequestDTO;
import com.example.adminservice.dto.response.ProxyResponseDTO;
import com.example.adminservice.dto.response.RoomProxyResponseDTO;
import com.example.adminservice.dto.response.RoomResponseDTO;
import com.example.adminservice.service.DepartmentService;
import com.example.adminservice.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/admin/proxy")
public class ProxyController {
    private final ProxyService proxyService;
    private final DepartmentService departmentService;

    @PutMapping("/room/unavailable")
    public Mono<ProxyResponseDTO> makeRoomAvailable(@RequestBody RoomUnavailableRequestDTO room) {
        return Mono.fromRunnable(() -> proxyService.makeRoomAvailable(room.getRoomId()))
                .thenReturn(new ProxyResponseDTO("make room unavailable successful"));
    }

    @PutMapping("/assignDoctor/{departmentName}")
    public RoomProxyResponseDTO assignDoctorDeptAndRoom(@PathVariable String departmentName) {
        return proxyService.assignRoomToDoctor(departmentName);
    }

    @GetMapping("/roomInfo/{roomId}")
    public RoomResponseDTO getRoomInformation(@PathVariable Integer roomId) {
        return proxyService.getRoomInfo(roomId);
    }

    @GetMapping("/get/{departmentId}")
    public boolean departmentExist(@PathVariable int departmentId) {
        return departmentService.departmentIsExist(departmentId);
    }
}
