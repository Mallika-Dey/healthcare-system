package com.example.cdssservice.controller;

import com.example.cdssservice.dto.request.ProgressRequestDTO;
import com.example.cdssservice.dto.response.ProxyResponseDTO;
import com.example.cdssservice.service.CdssService;
import com.example.cdssservice.service.ProgressHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/cdss/proxy")
public class ProxyController {
    private final ProgressHistoryService progressHistoryService;
    private final CdssService cdssService;

    @PostMapping("/save")
    public Mono<ProxyResponseDTO> addHealthProgress(@RequestBody ProgressRequestDTO progressRequestDTO) {
        return Mono
                .fromRunnable(() -> {
                    progressHistoryService.addHealthData(progressRequestDTO);
                    cdssService.addCDSS(progressRequestDTO);
                })
                .thenReturn(new ProxyResponseDTO("Data is successfully saved"));
    }
}
