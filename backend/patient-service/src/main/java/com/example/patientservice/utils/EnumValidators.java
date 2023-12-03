package com.example.patientservice.utils;

import com.example.patientservice.enums.*;
import com.example.patientservice.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class EnumValidators {
    public static AlcoholConsumption parseAlcoholConsumption(String alcoholConsumptionValue) {
        try {
            return AlcoholConsumption.valueOf(alcoholConsumptionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(),
                    "Invalid alcohol consumption level. Supported levels are NONE, OCCASIONAL, MODERATE, HEAVY.", HttpStatus.BAD_REQUEST);
        }
    }

    public static BloodPressure parseBloodPressure(String bloodPressureValue) {
        try {
            return BloodPressure.valueOf(bloodPressureValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(),
                    "Invalid blood pressure level. Supported levels are  HIGH, LOW, NORMAL.", HttpStatus.BAD_REQUEST);
        }
    }

    public static Gender parseGender(String userGender) {
        try {
            return Gender.valueOf(userGender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date()
                    , "Invalid gender. Supported genders are MALE and FEMALE.", HttpStatus.BAD_REQUEST);
        }
    }

    public static BloodGroup parseBloodGroup(String userBloodGroup) {
        try {
            return BloodGroup.valueOf(userBloodGroup.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(),
                    "Invalid blood group. Supported blood groups are A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, and O_NEGATIVE.", HttpStatus.BAD_REQUEST);
        }
    }

    public static GoalType parseGoalType(String userGoalType) {
        try {
            return GoalType.valueOf(userGoalType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid goal type. Supported goal types are LOSE_WEIGHT," +
                    " BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, and IMPROVE_SLEEP.", HttpStatus.BAD_REQUEST);
        }
    }

    public static ActivityLevel parseActivityLevel(String userActivityLevel) {
        try {
            return ActivityLevel.valueOf(userActivityLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid activity level. Supported activity levels are " +
                    "SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, and VERY_ACTIVE.", HttpStatus.BAD_REQUEST);
        }
    }

    public static ThirstLevel parseThirstLevel(String thirstLevel) {
        try {
            return ThirstLevel.valueOf(thirstLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(new Date(), "Invalid thirst level. Supported activity levels are " +
                    "MORE_THAN_USUAL, NORMAL.", HttpStatus.BAD_REQUEST);
        }
    }
}
