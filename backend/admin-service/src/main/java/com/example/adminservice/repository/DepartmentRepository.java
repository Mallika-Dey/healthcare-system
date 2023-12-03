package com.example.adminservice.repository;

import com.example.adminservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public Optional<Department> findByDeptName(String name);
}
