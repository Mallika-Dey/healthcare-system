package com.example.doctorservice.utils;

public class AppConstant {
    public static final String ROLE_PATIENT = "PATIENT";
    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final long TIME_PER_PATIENT = 15;
    public static final long MAX_RETRY_ATTEMPTS = 10;
    public static final long RETRY_TIME_GAP = 20;

    public static final String DOCTOR_REGISTER = "/api/v1/doctor/register";
    public static final String DOCTOR_GET_ALL = "/api/v1/doctor/getAll";
    public static final String DOCTOR_DEPARTMENT = "/api/v1/doctor/department/{deptId}";
    public static final String DOCTOR_BY_ID = "/api/v1/doctor/{doctorId}";
    public static final String DOCTOR_AVAILABLE = "/api/v1/doctor/available/{available}";
    public static final String DOCTOR_SEARCH = "/api/v1/doctor/search";
    public static final String DOCTOR_APPOINTMENT_TAKE = "/api/v1/doctor/appointment/take";
    public static final String DOCTOR_APPOINTMENT_GET = "/api/v1/doctor/appointment/get";
    public static final String PATIENT_UPCOMING_APPOINTMENTS = "/api/v1/doctor/appointment/get/patient/upcoming";
    public static final String DOCTOR_FREE_SLOT = "/api/v1/doctor/appointment/doctor/{userId}/freeSlot/{date}";
    public static final String DOCTOR_DASHBOARD = "/api/v1/doctor/appointment/dashboard";
    public static final String PROXY_API = "/api/v2/proxy/**";
    public static final String ACTUATOR_API = "/actuator/**";
}
