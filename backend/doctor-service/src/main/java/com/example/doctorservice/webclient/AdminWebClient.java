package com.example.doctorservice.webclient;

import com.example.doctorservice.dto.request.RoomUnavailableRequestDTO;
import com.example.doctorservice.dto.response.ProxyResponseDTO;
import reactor.core.publisher.Mono;

public interface AdminWebClient {
    public Mono<ProxyResponseDTO> makeRoomAvailable(RoomUnavailableRequestDTO room);
}
