package com.psajd.gbuz.services;

import com.psajd.gbuz.entities.KeyAssignment;
import com.psajd.gbuz.repositories.KeyAssignmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KeyAssignmentService {
    private final KeyAssignmentRepository keyAssignmentRepository;

    public KeyAssignmentService(KeyAssignmentRepository keyAssignmentRepository) {
        this.keyAssignmentRepository = keyAssignmentRepository;
    }

    public List<KeyAssignment> getAllKeyAssignments() {
        return keyAssignmentRepository.findAll();
    }

    public Optional<KeyAssignment> getKeyAssignmentById(Long id) {
        return keyAssignmentRepository.findById(id);
    }

    public KeyAssignment saveKeyAssignment(KeyAssignment keyAssignment) {
        return keyAssignmentRepository.save(keyAssignment);
    }

    public void deleteKeyAssignment(Long id) {
        keyAssignmentRepository.deleteById(id);
    }

    public List<KeyAssignment> findByEmployeeId(Long employeeId) {
        return keyAssignmentRepository.findByCertificateEmployeeId(employeeId);
    }

    public List<KeyAssignment> findByKeyCardId(Long keyCardId) {
        return keyAssignmentRepository.findByKeyCardId(keyCardId);
    }

    public List<KeyAssignment> searchKeyAssignments(Long keyCardId, Long employeeId, LocalDate startDate, LocalDate endDate) {
        return keyAssignmentRepository.findAll().stream()
                .filter(keyAssignment -> (keyCardId == null || keyAssignment.getKeyCard().getId().equals(keyCardId)) &&
                        (employeeId == null || keyAssignment.getCertificate().getEmployee().getId().equals(employeeId)) &&
                        (startDate == null || !keyAssignment.getAssignmentDate().isBefore(startDate)) &&
                        (endDate == null || !keyAssignment.getAssignmentDate().isAfter(endDate)))
                .toList();
    }
}
