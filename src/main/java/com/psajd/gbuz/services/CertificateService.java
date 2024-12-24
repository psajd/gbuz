package com.psajd.gbuz.services;

import com.psajd.gbuz.entities.Certificate;
import com.psajd.gbuz.repositories.CertificateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public Optional<Certificate> getCertificateById(Long id) {
        return certificateRepository.findById(id);
    }

    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }

    public List<Certificate> findCertificatesByEmployeeId(Long employeeId) {
        return certificateRepository.findByEmployeeId(employeeId);
    }

    public List<Certificate> findCertificatesByDateRange(LocalDate startDate, LocalDate endDate) {
        return certificateRepository.findByIssueDateBetween(startDate, endDate);
    }

    public List<Certificate> searchCertificates(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return certificateRepository.findAll().stream().filter(x -> (Objects.equals(x.getEmployee().getId(), employeeId))
                && Objects.equals(x.getIssueDate(), startDate)
                && Objects.equals(x.getExpirationDate(), endDate)
        ).toList();
    }
}