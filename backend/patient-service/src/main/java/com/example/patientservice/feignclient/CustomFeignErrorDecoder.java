package com.example.patientservice.feignclient;

import com.example.patientservice.exception.FeignCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class CustomFeignErrorDecoder {
    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                HttpStatus statusCode = HttpStatus.valueOf(response.status());
                return new FeignCustomException(statusCode, response, objectMapper);
            }
        };
    }
}