package com.example.cdssservice.service.impl;

import com.example.cdssservice.dto.request.ProgressRequestDTO;
import com.example.cdssservice.dto.response.RecommendationDTO;
import com.example.cdssservice.entity.Cdss;
import com.example.cdssservice.enums.Gender;
import com.example.cdssservice.enums.GoalType;
import com.example.cdssservice.exception.CustomException;
import com.example.cdssservice.repository.CdssRepository;
import com.example.cdssservice.service.CdssService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CdssServiceImpl implements CdssService {
    private final CdssRepository cdssRepository;

    @Override
    public void addCDSS(ProgressRequestDTO progressRequestDTO) {
        Cdss cdss = cdssRepository.findByUserId(progressRequestDTO.getUserId()).orElseGet(Cdss::new);

        String cardiacRisk = predictCardiacRisk(progressRequestDTO);
        String hypertensionRisk = predictHypertensionRisk(progressRequestDTO);
        String metabolicSyndromeRisk = predictMetabolicSyndromeRisk(progressRequestDTO);
        String risk = cardiacRisk + hypertensionRisk + metabolicSyndromeRisk;

        String recommendation = getCardiacRecommendations(cardiacRisk)
                + getHypertensionRecommendations(hypertensionRisk)
                + getMetabolicSyndromeRecommendations(metabolicSyndromeRisk);

        cdss.setUserId(progressRequestDTO.getUserId());
        cdss.setPreferredBMI(calculatePreferredBMI(progressRequestDTO));
        cdss.setRiskPrediction(risk);
        cdss.setRecommendation(recommendation);
        cdssRepository.save(cdss);
    }

    @Override
    public RecommendationDTO getRecommendation(Long authenticatedUserId) {
        Cdss cdss = cdssRepository.findByUserId(authenticatedUserId).orElseThrow(
                () -> new CustomException(new Date(), "Recommendation not exists", HttpStatus.BAD_REQUEST)
        );

        return RecommendationDTO
                .builder()
                .preferredBMI(cdss.getPreferredBMI())
                .healthRisk(cdss.getRiskPrediction())
                .recommendation(cdss.getRecommendation())
                .build();
    }

    private double calculateRiskScore(ProgressRequestDTO dto) {
        double score = 0.0;

        if (dto.getAge() > 45) score += 1;
        if (dto.getBmi() >= 25) score += 1;
        if ("HIGH".equalsIgnoreCase(dto.getBloodPressure())) score += 2;
        if (dto.isPreviousStroke()) score += 2;
        if (dto.isFamilyHistoryCardiovascularDisease()) score += 1;
        if (dto.isHighCholesterol()) score += 1;
        if (dto.isChestPain()) score += 2;
        if (dto.getSugarLevel() > 140) score += 1;
        if (dto.getFastingBloodGlucoseLevel() > 100) score += 1;
        if ("FREQUENT".equalsIgnoreCase(dto.getAlcoholConsumption())) score += 1;

        return score;
    }

    private String predictCardiacRisk(ProgressRequestDTO progressRequestDTO) {
        double riskScore = calculateRiskScore(progressRequestDTO);

        if (riskScore >= 5) {
            return "High risk of cardiac arrest.";
        } else if (riskScore >= 3) {
            return "Moderate risk of cardiac arrest.";
        }

        return "Low risk of cardiac arrest.";
    }

    private double calculateHypertensionRiskScore(ProgressRequestDTO dto) {
        double score = 0.0;
        if ("HIGH".equalsIgnoreCase(dto.getBloodPressure())) score += 3;
        if (dto.getBmi() >= 30) score += 1;
        if ("FREQUENT".equalsIgnoreCase(dto.getAlcoholConsumption())) score += 1;
        return score;
    }

    private String predictHypertensionRisk(ProgressRequestDTO progressRequestDTO) {
        double riskScore = calculateHypertensionRiskScore(progressRequestDTO);
        if (riskScore >= 3) {
            return "High risk of hypertension.";
        } else if (riskScore >= 2) {
            return "Moderate risk of hypertension.";
        }
        return "Low risk of hypertension.";
    }

    private double calculateMetabolicSyndromeRiskScore(ProgressRequestDTO dto) {
        double score = 0.0;
        if (dto.getBmi() >= 30) score += 1;
        if (dto.getSugarLevel() > 100) score += 1;
        if ("HIGH".equalsIgnoreCase(dto.getBloodPressure())) score += 1;
        if (dto.getFastingBloodGlucoseLevel() > 100) score += 1;
        return score;
    }

    private String predictMetabolicSyndromeRisk(ProgressRequestDTO progressRequestDTO) {
        double riskScore = calculateMetabolicSyndromeRiskScore(progressRequestDTO);
        if (riskScore >= 3) {
            return "High risk of metabolic syndrome.";
        } else if (riskScore >= 2) {
            return "Moderate risk of metabolic syndrome.";
        }
        return "Low risk of metabolic syndrome.";
    }

    private String getCardiacRecommendations(String riskLevel) {
        return switch (riskLevel) {
            case "High risk of cardiac arrest." ->
                    "Adopt a heart-healthy diet low in salt, saturated fats, and trans fats. " +
                            "Engage in regular moderate exercise. Avoid smoking and limit alcohol intake.";
            case "Moderate risk of cardiac arrest." -> "Monitor blood pressure and cholesterol levels regularly. " +
                    "Focus on balanced meals with plenty of fruits and vegetables. " +
                    "Stay physically active.";
            default -> "Maintain a healthy diet and regular physical activity. " +
                    "Regular health check-ups are recommended.";
        };
    }

    private String getHypertensionRecommendations(String riskLevel) {
        return switch (riskLevel) {
            case "High risk of hypertension." ->
                    "Reduce sodium intake, avoid high-cholesterol foods, and limit alcohol consumption. " +
                            "Regular exercise and stress management are important.";
            case "Moderate risk of hypertension." -> "Maintain a balanced diet with low sodium and high potassium. " +
                    "Stay active and monitor your blood pressure regularly.";
            default -> "Healthy lifestyle habits are key. Keep a balanced diet and regular exercise routine.";
        };
    }

    private String getMetabolicSyndromeRecommendations(String riskLevel) {
        return switch (riskLevel) {
            case "High risk of metabolic syndrome." ->
                    "Adopt a diet rich in whole grains, fruits, vegetables, and lean protein. " +
                            "Limit intake of sugars, trans fats, and processed foods. Increase physical activity.";
            case " Moderate risk of metabolic syndrome." ->
                    "Focus on weight management through a balanced diet and regular exercise. " +
                            "Limit sugary drinks and processed foods.";
            default -> "Maintain a healthy weight, eat a balanced diet, and stay active.";
        };
    }

    private double calculatePreferredBMI(ProgressRequestDTO progressRequestDTO) {
        double bmi = progressRequestDTO.getBmi();
        GoalType goalType = GoalType.valueOf(progressRequestDTO.getGoalType());
        Gender gender = Gender.valueOf(progressRequestDTO.getGender());
        double age = progressRequestDTO.getAge();

        double preferredBMI = bmi;

        switch (goalType) {
            case LOSE_WEIGHT -> preferredBMI -= 1.0;
            case BUILD_MUSCLE -> preferredBMI += 0.5;
            case IMPROVE_FITNESS -> preferredBMI += 0.3;
            case REDUCE_STRESS -> preferredBMI -= 0.2;
        }
        if (gender == Gender.MALE) {
            preferredBMI += 0.2;
        } else if (gender == Gender.FEMALE) {
            preferredBMI -= 0.2;
        }

        if (age < 30) {
            preferredBMI += 0.1;
        } else if (age < 50) {
            preferredBMI -= 0.1;
        } else {
            preferredBMI += 0.2;
        }

        return preferredBMI;
    }
}
