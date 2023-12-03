package com.example.doctorservice.repository;

import com.example.doctorservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {
    public List<Doctor> findByDeptId(Integer deptId);

    public Optional<Doctor> findByUserId(Long userId);
}
