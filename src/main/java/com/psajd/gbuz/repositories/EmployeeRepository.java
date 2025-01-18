package com.psajd.gbuz.repositories;

import com.psajd.gbuz.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    List<Employee> findByPositionContainingIgnoreCase(String position);
}