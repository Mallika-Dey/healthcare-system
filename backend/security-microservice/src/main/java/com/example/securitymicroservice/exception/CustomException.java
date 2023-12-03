package com.example.securitymicroservice.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private Date timestamp;
    private String message;
    private HttpStatus status;
}
