package com.example.doctorservice.service.impl;

import com.example.doctorservice.dto.request.AppointmentRequestDTO;
import com.example.doctorservice.dto.request.NotificationDTO;
import com.example.doctorservice.dto.response.*;
import com.example.doctorservice.entity.Appointment;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.enums.AppointmentType;
import com.example.doctorservice.exception.CustomException;
import com.example.doctorservice.feignclient.PatientFeignClient;
import com.example.doctorservice.repository.AppointmentRepository;
import com.example.doctorservice.service.AppointmentService;
import com.example.doctorservice.service.DoctorService;
import com.example.doctorservice.utils.AppConstant;
import com.example.doctorservice.utils.EnumValidator;
import com.example.doctorservice.webclient.PushNotificationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PushNotificationClient pushNotificationClient;
    private final PatientFeignClient patientFeignClient;

    @Override
    public void takeAppointment(AppointmentRequestDTO appointmentRequestDTO, Long patientId) {
        log.debug("Initiating takeAppointment process for patientId: {}", patientId);

        if (appointmentRequestDTO.getAppointmentDate().isBefore(LocalDate.now())
                || (appointmentRequestDTO.getAppointmentDate().equals(LocalDate.now())
                && appointmentRequestDTO.getStartTime().isBefore(LocalTime.now()))) {
            log.warn("Attempt to take an appointment with invalid time for patientId: {}", patientId);
            throw new CustomException(new Date(), "Invalid Appointment Time", HttpStatus.BAD_REQUEST);
        }

        if (appointmentRepository.existsByDoctorUserIdAndPatientUserIdAndAppointmentDate(
                appointmentRequestDTO.getDoctorUserId(), patientId,
                appointmentRequestDTO.getAppointmentDate())) {
            log.warn("Attempt to take multiple appointments for the same doctor and patientId: {}", patientId);
            throw new CustomException(new Date(), "Multiple appointment for same doctor not allowed"
                    , HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = doctorService.getDoctor(appointmentRequestDTO.getDoctorUserId());
        LocalTime appointmentEndTime =
                validateTime(doctor.getUserId(), appointmentRequestDTO, appointmentRequestDTO.getStartTime()
                        , doctor.getStartTime(), doctor.getEndTime());

        Appointment appointment = mapToAppointment(appointmentRequestDTO, patientId);
        appointmentRepository.save(appointment);
        log.info("Appointment saved for patientId: {}", patientId);

       saveNotification(appointment, doctor.getStartTime());
    }

    private void saveNotification(Appointment appointment, LocalTime doctorStartTime) {
        NotificationDTO notificationDTO = NotificationDTO
                .builder()
                .patientId(appointment.getPatientUserId())
                .doctorId(appointment.getDoctorUserId())
                .date(appointment.getAppointmentDate())
                .appointmentTime(appointment.getStartTime())
                .startTime(doctorStartTime)
                .build();

        //send notification details to notification microservice
        pushNotificationClient.saveNotification(notificationDTO)
                .subscribe(
                        response -> log.info("Successfully notification saved"),
                        ex -> log.error("Notification saved unsuccessful: " + ex.getMessage())
                );
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments(Long userId, LocalDate date) {
        List<Appointment> appointmentList = appointmentRepository
                .findByDoctorUserIdAndAppointmentDateOrderByPatientUserIdAsc(userId, date);
        List<Long> userList = appointmentList.stream().map(Appointment::getPatientUserId).toList();
        List<PatientProxyDTO> patientProxyDTOS = patientFeignClient.getByUserId(userList);

        List<AppointmentResponseDTO> appointmentResponseDTOList = new ArrayList<>();

        for (int i = 0; i < appointmentList.size(); i++) {
            appointmentResponseDTOList.add(
                    convertToAppointmentResponseDTO(appointmentList.get(i), patientProxyDTOS.get(i)));
        }
        return appointmentResponseDTOList;
    }

    @Override
    public List<AppointmentDoctorDTO> getPatientUpcomingAppointments(Long patientId) {
        List<Appointment> appointmentList = appointmentRepository
                .findAllByPatientUserIdAndAppointmentDateGreaterThanEqual(patientId, LocalDate.now());

        List<AppointmentDoctorDTO> appointmentDoctorDTOS = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            appointmentDoctorDTOS.add(mapToUpcomingAppointment(
                    appointment, doctorService.getDoctor(appointment.getDoctorUserId())));
        }
        return appointmentDoctorDTOS;
    }

    @Override
    public List<AvailableSlotDTO> getAvailableSlots(Long userId, LocalDate date) {
        Doctor doctor = doctorService.getDoctor(userId);
        LocalTime startTime = doctor.getStartTime();
        LocalTime endTime = doctor.getEndTime();
        List<AvailableSlotDTO> timeSlots = new ArrayList<>();

        while (!startTime.equals(endTime)) {
            LocalTime tmpEndTime = startTime.plusMinutes(AppConstant.TIME_PER_PATIENT);
            timeSlots.add(new AvailableSlotDTO(startTime, tmpEndTime));
            startTime = tmpEndTime;
        }

        List<AvailableSlotDTO> notAvailableSlotDTOS = appointmentRepository
                .findByDoctorUserIdAndAppointmentDate(userId, date)
                .stream()
                .map(appointment -> new AvailableSlotDTO(appointment.getStartTime(), appointment.getEndTime()))
                .toList();

        timeSlots.removeAll(notAvailableSlotDTOS);
        return timeSlots;
    }

    @Override
    public DashboardResponseDTO getDashboardData(long userId) {
        int totalPatient = totalPatientDoctorHad(userId);
        int totalAppointment = totalAppointmentDoctorHad(userId);
        int todayAppointment = appointmentRepository.findByDoctorUserIdAndAppointmentDate(userId, LocalDate.now()).size();
        int totalOnlineAppointments = appointmentRepository.countByDoctorUserIdAndAppointmentType(userId, AppointmentType.TELEMEDICINE);
        return DashboardResponseDTO
                .builder()
                .totalPatient(totalPatient)
                .totalAppointment(totalAppointment)
                .todayAppointment(todayAppointment)
                .totalOnlineAppointments(totalOnlineAppointments)
                .build();
    }

    private AppointmentDoctorDTO mapToUpcomingAppointment(Appointment appointment, Doctor doctor) {
        return AppointmentDoctorDTO
                .builder()
                .doctorName(doctor.getName())
                .imageUrl(doctor.getImage())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentType(appointment.getAppointmentType().toString())
                .build();
    }

    private int totalPatientDoctorHad(long userId) {
        return appointmentRepository.findByDoctorUserId(userId).stream().map(Appointment::getPatientUserId).collect(Collectors.toSet()).size();
    }

    private int totalAppointmentDoctorHad(long userId) {
        return appointmentRepository.findByDoctorUserId(userId).size();
    }

    private AppointmentResponseDTO convertToAppointmentResponseDTO(Appointment appointment, PatientProxyDTO patientProxyDTO) {
        return AppointmentResponseDTO
                .builder()
                .patientId(patientProxyDTO.getUserId())
                .name(patientProxyDTO.getName())
                .imageUrl(patientProxyDTO.getImageUrl())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentType(appointment.getAppointmentType().toString())
                .build();
    }

    private LocalTime validateTime(Long doctorUserId, AppointmentRequestDTO appointmentRequestDTO,
                                   LocalTime startTime, LocalTime doctorStartTime, LocalTime doctorEndTime) {
        long startTimeInMin = doctorStartTime.toSecondOfDay() / 60;
        long appointmentStartTimeInMin = startTime.toSecondOfDay() / 60;
        long timeDiff = appointmentStartTimeInMin - startTimeInMin;

        if (timeDiff < 0 || !(startTime.isBefore(doctorEndTime)) || timeDiff % AppConstant.TIME_PER_PATIENT != 0) {
            throw new CustomException(new Date(), "Select a valid time slot", HttpStatus.NOT_FOUND);
        }

        if (appointmentRepository.existsByDoctorUserIdAndAppointmentDateAndStartTime(doctorUserId,
                appointmentRequestDTO.getAppointmentDate(), startTime)) {
            throw new CustomException(new Date(), "appointment slot not available", HttpStatus.BAD_REQUEST);
        }
        return startTime.plusMinutes(AppConstant.TIME_PER_PATIENT);
    }

    private Appointment mapToAppointment(AppointmentRequestDTO appointmentRequestDTO, Long patientId) {
        return Appointment
                .builder()
                .patientUserId(patientId)
                .doctorUserId(appointmentRequestDTO.getDoctorUserId())
                .startTime(appointmentRequestDTO.getStartTime())
                .endTime(appointmentRequestDTO.getStartTime().plusMinutes(AppConstant.TIME_PER_PATIENT))
                .appointmentDate(appointmentRequestDTO.getAppointmentDate())
                .appointmentType(EnumValidator.parseAppointmentType(appointmentRequestDTO.getAppointmentType()))
                .build();
    }
}
