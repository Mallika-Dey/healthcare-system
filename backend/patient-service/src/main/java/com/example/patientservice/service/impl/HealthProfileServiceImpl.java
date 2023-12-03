package com.example.patientservice.service.impl;

import com.example.patientservice.dto.request.HealthProfileDTO;
import com.example.patientservice.dto.request.ProgressRequestDTO;
import com.example.patientservice.dto.response.HealthProfileResponseDTO;
import com.example.patientservice.entity.HealthProfile;
import com.example.patientservice.entity.PhysicalHealth;
import com.example.patientservice.exception.CustomException;
import com.example.patientservice.repository.HealthProfileRepository;
import com.example.patientservice.service.HealthProfileService;
import com.example.patientservice.service.PatientService;
import com.example.patientservice.utils.AppConstant;
import com.example.patientservice.utils.EnumValidators;
import com.example.patientservice.webclient.CdssWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthProfileServiceImpl implements HealthProfileService {
    private final HealthProfileRepository healthProfileRepository;
    private final PatientService patientService;
    private final CdssWebClient cdssWebClient;

    @Override
    public void createHealthProfile(HealthProfileDTO healthProfileDTO, Long userId) {
        if (healthProfileRepository.findByUserId(userId).isPresent()) {
            throw new CustomException(new Date(), "Health profile already exists", HttpStatus.BAD_REQUEST);
        }
        mapToHealthProfile(new HealthProfile(), healthProfileDTO, userId);
    }

    @Override
    public void updateHealthProfile(HealthProfileDTO healthProfileDTO, Long userId) {
        mapToHealthProfile(getHealthProfile(userId), healthProfileDTO, userId);
    }

    @Override
    public HealthProfileResponseDTO getUserHealthProfile(Long userId) {
        HealthProfile healthProfile = getHealthProfile(userId);
        return mapToHealthProfileDTO(healthProfile, patientService.getBloodGroup(userId));
    }

    private HealthProfileResponseDTO mapToHealthProfileDTO(HealthProfile healthProfile, String bloodGroup) {
        return HealthProfileResponseDTO
                .builder()
                .age(healthProfile.getAge())
                .weight(healthProfile.getWeight())
                .height(healthProfile.getHeight())
                .bloodGroup(bloodGroup)
                .bmi(healthProfile.getBmi())
                .bmr(healthProfile.getBmr())
                .goalType(healthProfile.getGoalType().toString())
                .activityLevel(healthProfile.getActivityLevel().toString())
                .gender(healthProfile.getGender().toString())
                .bloodPressure(healthProfile.getPhysicalHealth().getBloodPressure().toString())
                .smoke(healthProfile.getPhysicalHealth().isSmoke())
                .sinusitis(healthProfile.getPhysicalHealth().isSinusitis())
                .previousStroke(healthProfile.getPhysicalHealth().isPreviousStroke())
                .familyHistoryCardiovascularDisease(healthProfile.getPhysicalHealth().isFamilyHistoryCardiovascularDisease())
                .highCholesterol(healthProfile.getPhysicalHealth().isHighCholesterol())
                .chestPain(healthProfile.getPhysicalHealth().isChestPain())
                .sugarLevel(healthProfile.getPhysicalHealth().getSugarLevel())
                .fastingBloodGlucoseLevel(healthProfile.getPhysicalHealth().getFastingBloodGlucoseLevel())
                .alcoholConsumption(healthProfile.getPhysicalHealth().getAlcoholConsumption().toString())
                .thirstLevel(healthProfile.getPhysicalHealth().getThirstLevel().toString())
                .build();
    }

    private void saveToCDSS(HealthProfile healthProfile) {
        PhysicalHealth physicalHealth = healthProfile.getPhysicalHealth();

        ProgressRequestDTO progressRequestDTO = ProgressRequestDTO
                .builder()
                .userId(healthProfile.getUserId())
                .age(healthProfile.getAge())
                .bmi(healthProfile.getBmi())
                .bmr(healthProfile.getBmr())
                .gender(healthProfile.getGender().toString())
                .goalType(healthProfile.getGoalType().toString())
                .activityLevel(healthProfile.getActivityLevel().toString())
                .bloodPressure(physicalHealth.getBloodPressure().toString())
                .previousStroke(physicalHealth.isPreviousStroke())
                .familyHistoryCardiovascularDisease(physicalHealth.isFamilyHistoryCardiovascularDisease())
                .highCholesterol(physicalHealth.isHighCholesterol())
                .chestPain(physicalHealth.isChestPain())
                .sugarLevel(physicalHealth.getSugarLevel())
                .alcoholConsumption(physicalHealth.getAlcoholConsumption().toString())
                .fastingBloodGlucoseLevel(physicalHealth.getFastingBloodGlucoseLevel())
                .date(LocalDate.now())
                .build();

        cdssWebClient.addHealthProgress(progressRequestDTO)
                .subscribe(
                        response -> log.info("Successfully health data saved"),
                        ex -> log.error("Health data saved unsuccessful: " + ex.getMessage())
                );
    }

    private HealthProfile getHealthProfile(Long userId) {
        return healthProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(new Date(), "At first create your profile", HttpStatus.BAD_REQUEST));
    }

    private void mapToHealthProfile(HealthProfile healthProfile, HealthProfileDTO healthProfileDTO, Long userId) {
        PhysicalHealth physicalHealth = (healthProfile.getPhysicalHealth() == null) ?
                new PhysicalHealth() : healthProfile.getPhysicalHealth();
        physicalHealth = mapToPhysicalHealth(physicalHealth, healthProfileDTO);

        healthProfile.setAge(healthProfileDTO.getAge());
        healthProfile.setUserId(userId);
        healthProfile.setWeight(healthProfileDTO.getWeight());
        healthProfile.setHeight(healthProfileDTO.getHeight());
        healthProfile.setGender(EnumValidators.parseGender(healthProfileDTO.getGender()));
        healthProfile.setBmi(calculateBmi(healthProfile.getWeight(), healthProfile.getHeight()));
        healthProfile.setBmr(calculateBmr(healthProfile.getWeight(), healthProfile.getHeight(),
                healthProfileDTO.getAge(), healthProfileDTO.getGender()));
        healthProfile.setActivityLevel(EnumValidators.parseActivityLevel(healthProfileDTO.getActivityLevel()));
        healthProfile.setGoalType(EnumValidators.parseGoalType(healthProfileDTO.getGoalType()));
        healthProfile.setPhysicalHealth(physicalHealth);
        physicalHealth.setHealthProfile(healthProfile);
        healthProfileRepository.save(healthProfile);

        saveToCDSS(healthProfile);
    }

    private double calculateBmr(double weight, double height, int age, String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            return AppConstant.MALE_BMR_CONSTANT + (AppConstant.MALE_BMR_WEIGHT_COEFFICIENT * weight)
                    + (AppConstant.MALE_BMR_HEIGHT_COEFFICIENT * height * 100) - (AppConstant.MALE_BMR_AGE_COEFFICIENT * age);
        }
        return AppConstant.FEMALE_BMR_CONSTANT + (AppConstant.FEMALE_BMR_WEIGHT_COEFFICIENT * weight)
                + (AppConstant.FEMALE_BMR_HEIGHT_COEFFICIENT * height * 100) - (AppConstant.FEMALE_BMR_AGE_COEFFICIENT * age);

    }

    private double calculateBmi(double weight, double height) {
        return weight / (height * height);
    }

    private PhysicalHealth mapToPhysicalHealth(PhysicalHealth physicalHealth, HealthProfileDTO healthProfileDTO) {
        physicalHealth.setBloodPressure(EnumValidators.parseBloodPressure(healthProfileDTO.getBloodPressure()));
        physicalHealth.setSmoke(healthProfileDTO.getSmoke());
        physicalHealth.setSinusitis(healthProfileDTO.getSinusitis());
        physicalHealth.setFamilyHistoryCardiovascularDisease(healthProfileDTO.getFamilyHistoryCardiovascularDisease());
        physicalHealth.setPreviousStroke(healthProfileDTO.getPreviousStroke());
        physicalHealth.setHighCholesterol(healthProfileDTO.getHighCholesterol());
        physicalHealth.setFastingBloodGlucoseLevel(healthProfileDTO.getFastingBloodGlucoseLevel());
        physicalHealth.setSugarLevel(healthProfileDTO.getSugarLevel());
        physicalHealth.setAlcoholConsumption(EnumValidators.parseAlcoholConsumption(healthProfileDTO.getAlcoholConsumption()));
        physicalHealth.setThirstLevel(EnumValidators.parseThirstLevel(healthProfileDTO.getThirstLevel()));
        physicalHealth.setChestPain(healthProfileDTO.getChestPain());

        return physicalHealth;
    }
}
