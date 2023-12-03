/**
 * Service implementation for managing patient registration and related operations.
 * Registers a new patient and saves their information in the database.
 *
 * @param registerRequestDTO The registration request containing patient information.
 * @return An AuthenticationResponseDTO containing user information after registration.
 * @throws FeignCustomException If registration with the security service fails.
 */

package com.example.patientservice.service.impl;

import com.example.patientservice.dto.request.RegisterFeignDTO;
import com.example.patientservice.dto.request.RegisterRequestDTO;
import com.example.patientservice.dto.response.AuthenticationResponseDTO;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.enums.BloodGroup;
import com.example.patientservice.exception.AuthenticationException;
import com.example.patientservice.exception.CustomException;
import com.example.patientservice.exception.FeignCustomException;
import com.example.patientservice.feignclient.SecurityServiceClient;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.security.CustomWebAuthentication;
import com.example.patientservice.service.AuthenticationService;
import com.example.patientservice.utils.AppConstant;
import com.example.patientservice.utils.EnumValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PatientRepository patientRepository;
    private final SecurityServiceClient securityServiceClient;

    @Override
    public AuthenticationResponseDTO registerPatient(RegisterRequestDTO registerRequestDTO) {
        validateData(registerRequestDTO);

        AuthenticationResponseDTO authenticationResponseDTO = register(registerRequestDTO);
        Patient patient = mapToPatient(registerRequestDTO, authenticationResponseDTO);
        patientRepository.save(patient);
        return authenticationResponseDTO;
    }

    private void validateData(RegisterRequestDTO registerRequestDTO) {
        EnumValidators.parseBloodGroup(registerRequestDTO.getBloodGroup());
        if (!registerRequestDTO.getDateOfBirth().isBefore(LocalDate.now())) {
            throw new CustomException(new Date(), "Birth date must be before today", HttpStatus.BAD_REQUEST);
        }
    }

    private Patient mapToPatient(RegisterRequestDTO registerRequestDTO,
                                 AuthenticationResponseDTO authenticationResponseDTO) {
        boolean isInterested = registerRequestDTO.getInterestedInBloodDonate() != null ?
                registerRequestDTO.getInterestedInBloodDonate() : false;
        return Patient
                .builder()
                .userId(authenticationResponseDTO.getUserId())
                .name(registerRequestDTO.getName())
                .imageUrl(registerRequestDTO.getImageUrl())
                .address(registerRequestDTO.getAddress())
                .mobileNo(registerRequestDTO.getMobileNo())
                .dateOfBirth(registerRequestDTO.getDateOfBirth())
                .bloodGroup(BloodGroup.valueOf(registerRequestDTO.getBloodGroup()))
                .interestedInBloodDonate(isInterested)
                .build();
    }

    private RegisterFeignDTO mapToFeignDTO(RegisterRequestDTO registerRequestDTO) {
        return RegisterFeignDTO
                .builder()
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .role(AppConstant.ROLE_PATIENT)
                .build();
    }

    private AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        RegisterFeignDTO registerFeignDTO = mapToFeignDTO(registerRequestDTO);
        try {
            log.debug("Registering patient .........");
            return securityServiceClient.register(registerFeignDTO);
        } catch (FeignCustomException ex) {
            log.error("Registration failed ", ex);
            throw ex;
        }
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }

    @Override
    public String getTokenFromPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            CustomWebAuthentication details = (CustomWebAuthentication) authentication.getDetails();
            return "Bearer " + details.getJwtToken();
        }
        return null;
    }

    @Override
    public void handleAccessByUserRole(Long userId, String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long idFromToken = Long.parseLong(authentication.getName());
            if (!authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_" + role)) && userId != idFromToken) {
                throw new CustomException(new Date(), "You don't have access to another patient's health information"
                        , HttpStatus.BAD_REQUEST);
            }
        }
    }
}
