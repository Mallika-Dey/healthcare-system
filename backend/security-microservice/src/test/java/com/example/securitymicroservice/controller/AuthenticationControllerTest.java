package com.example.securitymicroservice.controller;

import com.example.securitymicroservice.DTO.request.LogInRequestDTO;
import com.example.securitymicroservice.DTO.request.RegisterRequestDTO;
import com.example.securitymicroservice.DTO.response.AuthenticationResponseDTO;
import com.example.securitymicroservice.DTO.response.LogInResponseDTO;
import com.example.securitymicroservice.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;
    private RegisterRequestDTO registerRequest;
    private LogInRequestDTO loginRequest;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequestDTO.builder()
                .name("Alice")
                .email("alice@example.com")
                .password("abcdefgh")
                .role("PATIENT")
                .build();

        loginRequest = LogInRequestDTO.builder()
                .email("tuli@example.com")
                .password("abcdefgh")
                .build();
    }

    @Test
    void whenRegister_thenReturns200() throws Exception {
        given(authenticationService.register(registerRequest)).willReturn(new AuthenticationResponseDTO());

        mockMvc.perform(post("/api/v2/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenAuthenticate_thenReturns200() throws Exception {
        given(authenticationService.authenticate(loginRequest)).willReturn(new LogInResponseDTO());

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
    }
}
