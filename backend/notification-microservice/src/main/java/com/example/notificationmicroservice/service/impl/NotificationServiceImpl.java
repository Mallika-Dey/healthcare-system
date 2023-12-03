package com.example.notificationmicroservice.service.impl;

import com.example.notificationmicroservice.dto.request.NotificationDTO;
import com.example.notificationmicroservice.entity.Notification;
import com.example.notificationmicroservice.entity.Role;
import com.example.notificationmicroservice.repository.NotificationRepository;
import com.example.notificationmicroservice.service.NotificationService;
import com.example.notificationmicroservice.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void saveNotifications(NotificationDTO notificationDTO) {
        log.info("Notification will be saved for patient and doctor");

        saveNotificationForDoctor(notificationDTO);
        saveNotificationForPatient(notificationDTO);
    }

    @Override
    public List<Notification> getNotificationsByDate(LocalDate date, LocalTime time) {
        return notificationRepository.findAllByDateAndStartTime(date, time);
    }

    private void saveNotificationForPatient(NotificationDTO notificationDTO) {
        LocalTime notificationTime = notificationDTO.getAppointmentTime().minusMinutes(2);
        LocalDate notificationDate = getUpdatedDate(notificationDTO.getDate(), notificationDTO.getAppointmentTime());

        Notification notification = Notification
                .builder()
                .userId(notificationDTO.getPatientId())
                .startTime(notificationTime)
                .date(notificationDate)
                .role(Role.valueOf(AppConstant.ROLE_PATIENT))
                .build();

        notificationRepository.save(notification);
    }

    private void saveNotificationForDoctor(NotificationDTO notificationDTO) {
        LocalTime notificationTime = notificationDTO.getStartTime().minusMinutes(2);
        LocalDate notificationDate = getUpdatedDate(notificationDTO.getDate(), notificationDTO.getStartTime());

        Optional<Notification> previousNotification = notificationRepository.findByUserIdAndDate
                (notificationDTO.getDoctorId(), notificationDTO.getDate());

        if (previousNotification.isPresent()) {
            log.info("Notification for doctor has already saved for day {}", notificationDTO.getDate());
            return;
        }
        Notification notification = new Notification();
        notification.setUserId(notificationDTO.getDoctorId());
        notification.setStartTime(notificationTime);
        notification.setDate(notificationDate);
        notification.setRole(Role.valueOf(AppConstant.ROLE_DOCTOR));

        notificationRepository.save(notification);
    }

    //Notification date calculation
    private LocalDate getUpdatedDate(LocalDate date, LocalTime time) {
        long startTimeInMin = time.toSecondOfDay() / 60;
        return (startTimeInMin - 2 < 0) ? date.minusDays(1) : date;
    }
}
