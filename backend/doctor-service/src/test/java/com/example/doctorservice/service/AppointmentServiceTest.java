package com.example.doctorservice.service;

import com.example.doctorservice.dto.request.AppointmentRequestDTO;
import com.example.doctorservice.dto.request.NotificationDTO;
import com.example.doctorservice.dto.response.ProxyResponseDTO;
import com.example.doctorservice.entity.Appointment;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.enums.Degree;
import com.example.doctorservice.enums.Designation;
import com.example.doctorservice.exception.CustomException;
import com.example.doctorservice.feignclient.PatientFeignClient;
import com.example.doctorservice.repository.AppointmentRepository;
import com.example.doctorservice.service.impl.AppointmentServiceImpl;
import com.example.doctorservice.webclient.PushNotificationClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DoctorService doctorService;
    @Mock
    private PushNotificationClient pushNotificationClient;
    @Mock
    private PatientFeignClient patientFeignClient;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Doctor createDoctor(Long doctorUserId) {
        return Doctor.builder()
                .userId(doctorUserId)
                .deptId(123)
                .roomId(456)
                .name("Dr. John Doe")
                .image("doctor_image_url.jpg")
                .departmentName("Cardiology")
                .medicalName("City Hospital")
                .degree(Degree.MD)
                .designation(Designation.SENIOR_CONSULTANT)
                .specialization("Cardiology")
                .yearOfExperience(10)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .noOfDailyPatient(30)
                .available(true)
                .build();
    }

    @Test
    public void takeAppointment_Success() {
        Long doctorUserId = 1L;
        Long patientId = 2L;
        LocalDate appointmentDate = LocalDate.now().plusDays(1);

        AppointmentRequestDTO requestDTO = AppointmentRequestDTO.builder()
                .doctorUserId(doctorUserId)
                .startTime(LocalTime.of(10, 0))
                .appointmentDate(appointmentDate)
                .appointmentType("IN_PERSON")
                .build();

        Doctor doctor = createDoctor(doctorUserId);

        when(doctorService.getDoctor(doctorUserId)).thenReturn(doctor);
        when(appointmentRepository.existsByDoctorUserIdAndPatientUserIdAndAppointmentDate(
                patientId, patientId, appointmentDate))
                .thenReturn(false);
        when(pushNotificationClient.saveNotification(any(NotificationDTO.class)))
                .thenReturn(Mono.just(new ProxyResponseDTO()));

        appointmentService.takeAppointment(requestDTO, patientId);

        verify(appointmentRepository, times(1)).save(any(Appointment.class));


        verify(pushNotificationClient, times(1))
                .saveNotification(any(NotificationDTO.class));
    }

    //test for invalid appointment type
    @Test
    public void appointmentFailedInValidEnum() {
        Long doctorUserId = 1L;
        Long patientId = 2L;
        LocalDate appointmentDate = LocalDate.now().plusDays(1);

        AppointmentRequestDTO requestDTO = AppointmentRequestDTO.builder()
                .doctorUserId(doctorUserId)
                .startTime(LocalTime.of(10, 0))
                .appointmentDate(appointmentDate)
                .appointmentType("In-Person")
                .build();

        Doctor doctor = createDoctor(doctorUserId);

        when(doctorService.getDoctor(doctorUserId)).thenReturn(doctor);
        when(appointmentRepository.existsByDoctorUserIdAndPatientUserIdAndAppointmentDate(
                patientId, patientId, appointmentDate))
                .thenReturn(false);

        assertThrows(CustomException.class, () -> {
            appointmentService.takeAppointment(requestDTO, patientId);
        });

        verify(appointmentRepository, never()).save(any(Appointment.class));
        verify(pushNotificationClient, never()).saveNotification(any(NotificationDTO.class));
    }

    //invalid appointment day
    @Test
    public void takeAppointment_InvalidDay() {
        LocalDate appointmentDate = LocalDate.now().minusDays(1);

        AppointmentRequestDTO requestDTO = AppointmentRequestDTO.builder()
                .doctorUserId(1L)
                .startTime(LocalTime.of(10, 0))
                .appointmentDate(appointmentDate)
                .appointmentType("In-Person")
                .build();

        assertThrows(CustomException.class, () -> {
            appointmentService.takeAppointment(requestDTO, 2L);
        });
    }
}
