package com.psajd.gbuz;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.psajd.gbuz.entities.KeyAssignment;
import com.psajd.gbuz.repositories.KeyAssignmentRepository;
import com.psajd.gbuz.services.KeyAssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KeyAssignmentServiceTest {

    @Mock
    private KeyAssignmentRepository keyAssignmentRepository;

    @InjectMocks
    private KeyAssignmentService keyAssignmentService;

    private KeyAssignment keyAssignment;

    @BeforeEach
    void setUp() {
        // Initialize mock KeyAssignment
        keyAssignment = new KeyAssignment();
        keyAssignment.setId(1L);
        keyAssignment.setAssignmentDate(LocalDate.now());
        // Initialize other fields if necessary
    }

    @Test
    void testGetAllKeyAssignments() {
        when(keyAssignmentRepository.findAll()).thenReturn(Collections.singletonList(keyAssignment));

        List<KeyAssignment> keyAssignments = keyAssignmentService.getAllKeyAssignments();

        assertThat(keyAssignments).isNotEmpty();
        assertThat(keyAssignments.size()).isEqualTo(1);
        assertThat(keyAssignments.get(0).getId()).isEqualTo(keyAssignment.getId());
    }

    @Test
    void testGetKeyAssignmentById() {
        when(keyAssignmentRepository.findById(1L)).thenReturn(Optional.of(keyAssignment));

        Optional<KeyAssignment> result = keyAssignmentService.getKeyAssignmentById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void testSaveKeyAssignment() {
        when(keyAssignmentRepository.save(keyAssignment)).thenReturn(keyAssignment);

        KeyAssignment savedKeyAssignment = keyAssignmentService.saveKeyAssignment(keyAssignment);

        assertThat(savedKeyAssignment).isNotNull();
        assertThat(savedKeyAssignment.getId()).isEqualTo(keyAssignment.getId());
    }

    @Test
    void testDeleteKeyAssignment() {
        doNothing().when(keyAssignmentRepository).deleteById(1L);

        keyAssignmentService.deleteKeyAssignment(1L);

        verify(keyAssignmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchKeyAssignments() {
        when(keyAssignmentRepository.findAll()).thenReturn(Collections.singletonList(keyAssignment));

        List<KeyAssignment> result = keyAssignmentService.searchKeyAssignments(null, null, null, null);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(keyAssignment.getId());
    }
}
