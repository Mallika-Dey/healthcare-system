package com.example.doctorservice.repository;

import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.utils.EnumValidator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {
    public static Specification<Doctor> dynamicQuery(
            String name, String department, String designation, Integer experience, String specialization) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (department != null && !department.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("departmentName"), department));
            }
            if (designation != null && !designation.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("designation"), EnumValidator.validateDesignation(designation)));
            }
            if (experience != null) {
                predicates.add(criteriaBuilder.ge(root.get("yearOfExperience"), experience));
            }
            if (specialization != null && !specialization.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("specialization"), specialization));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
