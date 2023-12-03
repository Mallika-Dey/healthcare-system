package com.example.inventoryservice.utils;

import com.example.inventoryservice.enums.EquipmentCategory;
import com.example.inventoryservice.enums.MedicineCategory;
import com.example.inventoryservice.enums.MedicineType;
import com.example.inventoryservice.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class EnumValidator {
    public static MedicineCategory parseMedicineCategory(String category) {
        try {
            return MedicineCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid medicine category. Category should be" +
                    " ANTIBIOTICS, ANALGESICS, ANTIVIRALS, VACCINES, ANTIDEPRESSANTS, ANTIHISTAMINES", HttpStatus.BAD_REQUEST);
        }
    }

    public static MedicineType parseMedicineType(String type) {
        try {
            return MedicineType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid medicine type. Category should be" +
                    "   TABLET, SYRUP, INJECTION, OINTMENT, DROPS,", HttpStatus.BAD_REQUEST);
        }
    }

    public static EquipmentCategory parseEquipmentCategory(String category) {
        try {
            return EquipmentCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid equipment category. Category should be" +
                    " DIAGNOSTIC, SURGICAL, LABORATORY, PATIENT_MONITORING, LIFE_SUPPORT", HttpStatus.BAD_REQUEST);
        }
    }
}
