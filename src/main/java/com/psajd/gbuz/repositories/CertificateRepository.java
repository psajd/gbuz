package com.psajd.gbuz.repositories;

import com.psajd.gbuz.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByEmployeeId(Long employeeId);
    List<Certificate> findByIssueDateBetween(LocalDate issueDate, LocalDate issueDate2);
}