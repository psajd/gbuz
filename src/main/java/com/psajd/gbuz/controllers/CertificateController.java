package com.psajd.gbuz.controllers;

import com.psajd.gbuz.entities.Certificate;
import com.psajd.gbuz.services.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<Certificate> getAllCertificates() {
        return certificateService.getAllCertificates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificateById(@PathVariable Long id) {
        Optional<Certificate> certificate = certificateService.getCertificateById(id);
        return certificate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.saveCertificate(certificate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable Long id, @RequestBody Certificate updatedCertificate) {
        return certificateService.getCertificateById(id)
                .map(certificate -> {
                    updatedCertificate.setId(id);
                    return ResponseEntity.ok(certificateService.saveCertificate(updatedCertificate));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        if (certificateService.getCertificateById(id).isPresent()) {
            certificateService.deleteCertificate(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Certificate> searchCertificates(@RequestParam(required = false) Long employeeId,
                                                @RequestParam(required = false) LocalDate startDate,
                                                @RequestParam(required = false) LocalDate endDate) {
        return certificateService.searchCertificates(employeeId, startDate, endDate);
    }
}