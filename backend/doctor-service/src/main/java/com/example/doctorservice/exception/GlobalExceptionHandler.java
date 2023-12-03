package com.example.doctorservice.exception;

import com.example.doctorservice.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {

        return ResponseHandler.generateResponse(ex.getTimestamp(), ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(FeignCustomException.class)
    public ResponseEntity<?> handleFeignCustomException(FeignCustomException ex) {
        return ResponseHandler.generateResponse(ex.getErrorDetails().getTimestamp(),
                ex.getErrorDetails().getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            errorMessage.add(message);
        });
        return ResponseHandler.generateResponse(new Date(), errorMessage.toString(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        return ResponseHandler.generateResponse(new Date(), "Request Failed",
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}