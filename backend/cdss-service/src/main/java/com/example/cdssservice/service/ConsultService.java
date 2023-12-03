package com.example.cdssservice.service;

import com.example.cdssservice.dto.response.ConsultResponseDTO;
import com.example.cdssservice.enums.ConsultStep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultService {
    private ConsultStep currentStage = ConsultStep.START;

    public ConsultResponseDTO getNextStep(String userResponse) {
        switch (currentStage) {
            case START -> {
                currentStage = ConsultStep.FIRST_STEP;
                return new ConsultResponseDTO("What is your problem?", List.of("Mental", "Physical"), false);
            }
            case FIRST_STEP -> {
                if ("mental".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.SECOND_STEP;
                    return new ConsultResponseDTO("Which of these are you experiencing?", List.of("Stress", "Anxiety", "Sleep Disturbances", "Others"), false);
                } else if ("physical".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.SECOND_STEP;
                    return new ConsultResponseDTO("Is your problem related to:", List.of("Internal", "Injury"), false);
                }
            }

            case SECOND_STEP -> {
                if ("internal".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.THIRD_STEP;
                    return new ConsultResponseDTO("Do you have symptoms related to:", List.of("Fever", "Cold"), false);
                } else if ("injury".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.THIRD_STEP;
                    return new ConsultResponseDTO("Is the injury a result of:", List.of("Recent Accident", "Recurring Issue"), false);
                } else if ("stress".equalsIgnoreCase(userResponse) || "anxiety".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.THIRD_STEP;
                    return new ConsultResponseDTO("Is this a:", List.of("Long Period", "Short Period"), false);
                } else if ("Sleep Disturbances".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.THIRD_STEP;
                    return new ConsultResponseDTO("Have you previously taken medicine:", List.of("Yes", "No"), false);
                } else if ("others".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("You should go to 'Neurology Department'.", null, true);
                }
            }

            case THIRD_STEP -> {
                switch (userResponse.toLowerCase()) {
                    case "fever", "cold" -> {
                        currentStage = ConsultStep.END;
                        return new ConsultResponseDTO("Based on your symptoms, you should visit a medicine doctor.", null, true);
                    }
                    case "yes", "no" -> {
                        if ("yes".equalsIgnoreCase(userResponse)) {
                            currentStage = ConsultStep.FOURTH_STEP;
                            return new ConsultResponseDTO("Are you currently following any treatment or medication regimen prescribed by your doctor?", List.of("Yes", "No"), false);
                        } else {
                            currentStage = ConsultStep.END;
                            return ConsultResponseDTO
                                    .builder()
                                    .message("Consult with a sleep specialist for alternative treatment options.")
                                    .conversationEnded(true)
                                    .build();
                        }
                    }
                    case "short period", "long period" -> {
                        if ("short period".equalsIgnoreCase(userResponse)) {
                            currentStage = ConsultStep.END;
                            return new ConsultResponseDTO("Observation and stress management techniques may be helpful. If symptoms worsen, please consult a healthcare provider.", null, true);
                        } else {
                            currentStage = ConsultStep.FOURTH_STEP;
                            return new ConsultResponseDTO("Would you describe your condition as?", List.of("Chronic", "Recurrent"), false);
                        }
                    }
                    case "recurring issue", "recent accident" -> {
                        if ("recurring issue".equalsIgnoreCase(userResponse)) {
                            currentStage = ConsultStep.END;
                            return new ConsultResponseDTO("You should receive any prior treatment for this injury, such as Physiotherapy or Surgery or consult your previous doctor.", null, true);
                        } else {
                            currentStage = ConsultStep.FOURTH_STEP;
                            return new ConsultResponseDTO("Are you experiencing?", List.of("Severe Pain", "Swelling", "Restricted Movement"), false);
                        }
                    }
                    default -> {
                        return new ConsultResponseDTO("Invalid response. Please try again.", null, false);
                    }
                }
            }

            case FOURTH_STEP -> {
                if ("chronic".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO(" For chronic physical conditions, seeing a specialist in the respective area (like cardiology, endocrinology, etc.) is recommended.", null, true);
                } else if ("no".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("You should go 'Physiologist' ", null, true);
                } else if ("severe pain".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.FIFTH_STEP;
                    return new ConsultResponseDTO("How is your pain? Is the pain", List.of("Localized to one area", "Radiating to other parts of the body"), false);
                } else if ("yes".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("You should go your previous doctor again. For better you can go 'Abnormal psychology Department' ", null, true);
                } else if ("swelling".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.FIFTH_STEP;
                    return new ConsultResponseDTO("Has the swelling been increasing since the injury occurred?", List.of("YES", "NO"), false);
                } else if ("restricted movement".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.FIFTH_STEP;
                    return new ConsultResponseDTO("Is the restriction in movement accompanied by", List.of("Severe Pain", "Inability to use the limb"), false);
                } else if ("recurrent".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("You should go your previous doctor again. For better you can go 'Abnormal psychology Department' ", null, true);
                }
            }

            case FIFTH_STEP -> {
                if ("localized to one area".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("This could indicate a serious injury. Immediate medical attention in an emergency department is advised. ", null, true);
                } else if ("radiating to other parts of the body".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.SIX_STEP;
                    return new ConsultResponseDTO("Is the radiating pain accompanied by",
                            List.of("Numbness", "Loss of movement in any limb"), false);
                } else if ("yes".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("It's important to have the swelling evaluated, especially if it's increasing. " +
                            "A visit to an urgent care center or your healthcare provider is advisable. ", null, true);
                } else if ("no".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("Monitor the swelling. If it doesn't decrease or is accompanied by increasing pain or other symptoms, seek medical attention. ", null, true);
                } else if ("severe pain".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("Severe pain, particularly after an injury, warrants immediate medical attention. It's crucial " +
                            "to seek care at an emergency department or urgent care center to assess the extent of the injury and " +
                            "receive appropriate treatment. Severe pain can be a sign of a serious issue such as a fracture, " +
                            "internal injury, or other significant trauma that requires prompt evaluation by healthcare professionals. ", null, true);
                } else if ("inability to use the limb".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("While the restriction in movement may not be causing severe pain, it is" +
                            " still advisable to have it evaluated by a healthcare professional as soon as possible. An" +
                            " orthopedic specialist or your primary care physician can assess the extent of the injury" +
                            " and recommend appropriate treatment or further diagnostics. ", null, true);
                }
            }

            case SIX_STEP -> {
                if ("numbness".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.SEVEN_STEP;
                    return new ConsultResponseDTO("Is the numbness", List.of("Localized to one specific area", "Spreading to different parts of your body"), false);
                } else if ("loss of movement in any limb".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.SEVEN_STEP;
                    return new ConsultResponseDTO("Is the continuous difficulty with movement accompanied by", List.of("Pain", "Weakness"), false);
                }
            }

            case SEVEN_STEP -> {
                if ("localized to one specific area".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("Localized numbness, especially if sudden, may require immediate medical " +
                            "attention to rule out nerve compression or other injuries. Please consider visiting an urgent" +
                            " care center or your healthcare provider for an evaluation.", null, true);
                } else if ("spreading to different parts of your body".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("Spreading numbness accompanied by other symptoms can be indicative of a " +
                            "more serious condition. Please seek immediate medical attention.", null, true);
                } else if ("pain".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("If injury-related: \"Pain resulting from an injury, especially if severe" +
                            " or worsening, should be promptly evaluated by a healthcare professional. Visiting an emergency" +
                            " department or an urgent care center is advisable for acute or severe injuries. If unknown" +
                            " origin: \n Pain of unknown origin should not be ignored. It is important to consult with a" +
                            " healthcare provider for a thorough examination to determine the underlying cause and " +
                            "appropriate treatment.", null, true);
                } else if ("weakness".equalsIgnoreCase(userResponse)) {
                    currentStage = ConsultStep.END;
                    return new ConsultResponseDTO("If localized: \"Localized weakness, particularly if sudden or after an " +
                            "injury, can indicate a specific issue such as nerve compression or muscle injury. It is important" +
                            " to seek medical attention from a healthcare provider or a specialist such as a neurologist or " +
                            "orthopedic doctor. \n If general: General weakness can be a symptom of a wide range of conditions" +
                            ", from nutritional deficiencies to more serious systemic issues. It is recommended to see your " +
                            "primary care physician for a comprehensive evaluation to identify the cause and appropriate " +
                            "treatment.", null, true);
                }
            }
        }

        currentStage = ConsultStep.START;
        return new ConsultResponseDTO("Invalid response. Please start again.", null, true);
    }

    public void resetConversation() {
        currentStage = ConsultStep.START;
    }
}
