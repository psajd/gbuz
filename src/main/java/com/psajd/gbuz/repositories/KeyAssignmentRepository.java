package com.psajd.gbuz.repositories;

import com.psajd.gbuz.entities.KeyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyAssignmentRepository extends JpaRepository<KeyAssignment, Long> {
    List<KeyAssignment> findByCertificateEmployeeId(Long employeeId);
    List<KeyAssignment> findByKeyCardId(Long keyCardId);
}