package com.example.cdssservice.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultResponseDTO {
    private String message;
    private List<String> options;
    private boolean conversationEnded;
}
