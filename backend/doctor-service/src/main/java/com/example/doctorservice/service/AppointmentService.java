package com.example.doctorservice.service;

import com.example.doctorservice.dto.request.AppointmentRequestDTO;
import com.example.doctorservice.dto.response.AppointmentDoctorDTO;
import com.example.doctorservice.dto.response.AppointmentResponseDTO;
import com.example.doctorservice.dto.response.AvailableSlotDTO;
import com.example.doctorservice.dto.response.DashboardResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    public void takeAppointment(AppointmentRequestDTO appointmentRequestDTO, Long patientId);

    public List<AppointmentResponseDTO> getAllAppointments(Long userId, LocalDate date);

    public List<AppointmentDoctorDTO> getPatientUpcomingAppointments(Long patientId);

    public List<AvailableSlotDTO> getAvailableSlots(Long userId, LocalDate date);

    public DashboardResponseDTO getDashboardData(long userId);
}
