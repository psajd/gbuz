package com.psajd.gbuz;

import com.psajd.gbuz.entities.KeyAssignment;
import com.psajd.gbuz.entities.KeyCard;
import com.psajd.gbuz.entities.Certificate;
import com.psajd.gbuz.entities.Employee;
import com.psajd.gbuz.services.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private KeyAssignment keyAssignment;

    @Mock
    private KeyCard keyCard;

    @Mock
    private Certificate certificate;

    @Mock
    private Employee employee;

    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("ru"));

        lenient().when(keyAssignment.getAssignmentDate()).thenReturn(LocalDate.of(2025, 1, 18));
        lenient().when(keyAssignment.getCertificate()).thenReturn(certificate);
        lenient().when(keyAssignment.getKeyCard()).thenReturn(keyCard);

        lenient().when(keyCard.getSerialNumber()).thenReturn("123456789");
        lenient().when(certificate.getEmployee()).thenReturn(employee);
        lenient().when(employee.getFullName()).thenReturn("Иванов Иван Иванович");
    }


    @Test
    void testKeyAssignmentDocument() {
        ResponseEntity<byte[]> response = documentService.keyAssignmentDocument(1L, keyAssignment, formatter);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION)).contains("attachment; filename=act-1.docx");

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        verify(keyAssignment, times(3)).getAssignmentDate();
        verify(keyAssignment, times(1)).getCertificate();
        verify(keyAssignment, times(1)).getKeyCard();
        verify(keyCard, times(1)).getSerialNumber();
        verify(certificate, times(1)).getEmployee();
        verify(employee, times(1)).getFullName();
    }

    @Test
    void testKeyAssignmentDocument_ExceptionHandling() {
        doThrow(new RuntimeException("Error generating document")).when(keyAssignment).getAssignmentDate();

        ResponseEntity<byte[]> response = documentService.keyAssignmentDocument(1L, keyAssignment, formatter);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNull();
    }
}
