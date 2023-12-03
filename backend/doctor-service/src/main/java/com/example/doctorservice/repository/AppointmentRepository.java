package com.example.doctorservice.repository;

import com.example.doctorservice.entity.Appointment;
import com.example.doctorservice.enums.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    public boolean existsByDoctorUserIdAndPatientUserIdAndAppointmentDate(
            Long userId,
            Long patientId,
            LocalDate appointmentDate
    );

    public boolean existsByDoctorUserIdAndAppointmentDateAndStartTime(
            Long userId,
            LocalDate appointmentDate,
            LocalTime appointmentStartTime
    );

    public List<Appointment> findByDoctorUserIdAndAppointmentDateOrderByPatientUserIdAsc(
            Long userId,
            LocalDate appointmentDate
    );

    public List<Appointment> findByDoctorUserIdAndAppointmentDate(
            Long userId,
            LocalDate appointmentDate
    );

    public List<Appointment> findByDoctorUserId(Long userId);

    public Integer countByDoctorUserIdAndAppointmentType(Long doctorUserId, AppointmentType appointmentType);

    public List<Appointment> findAllByPatientUserIdAndAppointmentDateGreaterThanEqual(
            Long patientUserId, LocalDate date);
}
