package com.example.doctorservice.utils;

import com.example.doctorservice.enums.AppointmentType;
import com.example.doctorservice.enums.Degree;
import com.example.doctorservice.enums.Designation;
import com.example.doctorservice.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class EnumValidator {
    public static AppointmentType parseAppointmentType(String type) {
        try {
            return AppointmentType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid appointment type. Supported appointment types are " +
                    "IN_PERSON and TELEMEDICINE.", HttpStatus.BAD_REQUEST);
        }
    }

    public static Designation validateDesignation(String designation) {
        try {
            return Designation.valueOf(designation.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid designation. Supported designations are " +
                    "SENIOR_CONSULTANT, PROFESSOR, ASSOCIATE_PROFESSOR, JUNIOR_DOCTOR, RESIDENT, " +
                    "CHIEF_PHYSICIAN, MEDICAL_OFFICER, SURGEON, GENERAL_PRACTITIONER.", HttpStatus.BAD_REQUEST);
        }
    }

    public static Degree validateDegree(String degree) {
        try {
            return Degree.valueOf(degree.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid designation. Supported degrees are " +
                    "MBBS, MD, DO, BDS, MS.", HttpStatus.BAD_REQUEST);
        }
    }
}
