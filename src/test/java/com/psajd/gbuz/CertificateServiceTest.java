package com.psajd.gbuz;

import com.psajd.gbuz.entities.Certificate;
import com.psajd.gbuz.entities.Employee;
import com.psajd.gbuz.repositories.CertificateRepository;
import com.psajd.gbuz.services.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CertificateServiceTest {

    private CertificateService certificateService;
    private CertificateRepository certificateRepository;

    @BeforeEach
    void setUp() {
        certificateRepository = mock(CertificateRepository.class);
        certificateService = new CertificateService(certificateRepository);
    }

    @Test
    void getAllCertificates_shouldReturnListOfCertificates() {
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        certificate1.setIssueDate(LocalDate.now());
        certificate1.setExpirationDate(LocalDate.now().plusYears(1));
        certificate1.setEmployee(new Employee());

        Certificate certificate2 = new Certificate();
        certificate2.setId(2L);
        certificate2.setIssueDate(LocalDate.now());
        certificate2.setExpirationDate(LocalDate.now().plusYears(1));
        certificate2.setEmployee(new Employee());

        when(certificateRepository.findAll()).thenReturn(Arrays.asList(certificate1, certificate2));

        List<Certificate> certificates = certificateService.getAllCertificates();

        assertEquals(2, certificates.size());
        verify(certificateRepository, times(1)).findAll();
    }

    @Test
    void getCertificateById_shouldReturnCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpirationDate(LocalDate.now().plusYears(1));
        certificate.setEmployee(new Employee());

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));

        Optional<Certificate> foundCertificate = certificateService.getCertificateById(1L);

        assertTrue(foundCertificate.isPresent());
        assertEquals(1L, foundCertificate.get().getId());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void saveCertificate_shouldReturnSavedCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpirationDate(LocalDate.now().plusYears(1));
        certificate.setEmployee(new Employee());

        when(certificateRepository.save(certificate)).thenReturn(certificate);

        Certificate savedCertificate = certificateService.saveCertificate(certificate);

        assertNotNull(savedCertificate.getId());
        verify(certificateRepository, times(1)).save(certificate);
    }

    @Test
    void deleteCertificate_shouldCallDeleteById() {
        doNothing().when(certificateRepository).deleteById(1L);

        certificateService.deleteCertificate(1L);

        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    void findCertificatesByEmployeeId_shouldReturnCertificates() {
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        certificate1.setIssueDate(LocalDate.now());
        certificate1.setExpirationDate(LocalDate.now().plusYears(1));
        certificate1.setEmployee(new Employee());

        Certificate certificate2 = new Certificate();
        certificate2.setId(2L);
        certificate2.setIssueDate(LocalDate.now());
        certificate2.setExpirationDate(LocalDate.now().plusYears(1));
        certificate2.setEmployee(new Employee());

        when(certificateRepository.findByEmployeeId(1L)).thenReturn(Arrays.asList(certificate1, certificate2));

        List<Certificate> certificates = certificateService.findCertificatesByEmployeeId(1L);

        assertEquals(2, certificates.size());
        verify(certificateRepository, times(1)).findByEmployeeId(1L);
    }

    @Test
    void findCertificatesByDateRange_shouldReturnCertificates() {
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        certificate1.setIssueDate(LocalDate.now());
        certificate1.setExpirationDate(LocalDate.now().plusYears(1));
        certificate1.setEmployee(new Employee());

        Certificate certificate2 = new Certificate();
        certificate2.setId(2L);
        certificate2.setIssueDate(LocalDate.now());
        certificate2.setExpirationDate(LocalDate.now().plusYears(1));
        certificate2.setEmployee(new Employee());

        when(certificateRepository.findByIssueDateBetween(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)))
                .thenReturn(Arrays.asList(certificate1, certificate2));

        List<Certificate> certificates = certificateService.findCertificatesByDateRange(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        assertEquals(2, certificates.size());
        verify(certificateRepository, times(1)).findByIssueDateBetween(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
    }
}
