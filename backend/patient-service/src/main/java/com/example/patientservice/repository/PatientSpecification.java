package com.example.patientservice.repository;

import com.example.patientservice.entity.Patient;
import com.example.patientservice.utils.EnumValidators;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {
    public static Specification<Patient> dynamicQuery(String bloodGroup) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("bloodGroup"), EnumValidators.parseBloodGroup(bloodGroup)));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("interestedInBloodDonate")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
