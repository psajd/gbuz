package com.psajd.gbuz.controllers;

import com.psajd.gbuz.entities.KeyAssignment;
import com.psajd.gbuz.services.KeyAssignmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/keyassignments")
public class KeyAssignmentController {

    private final KeyAssignmentService keyAssignmentService;

    public KeyAssignmentController(KeyAssignmentService keyAssignmentService) {
        this.keyAssignmentService = keyAssignmentService;
    }

    @GetMapping
    public List<KeyAssignment> getAllKeyAssignments() {
        return keyAssignmentService.getAllKeyAssignments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeyAssignment> getKeyAssignmentById(@PathVariable Long id) {
        Optional<KeyAssignment> keyAssignment = keyAssignmentService.getKeyAssignmentById(id);
        return keyAssignment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public KeyAssignment createKeyAssignment(@RequestBody KeyAssignment keyAssignment) {
        return keyAssignmentService.saveKeyAssignment(keyAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyAssignment(@PathVariable Long id) {
        if (keyAssignmentService.getKeyAssignmentById(id).isPresent()) {
            keyAssignmentService.deleteKeyAssignment(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<KeyAssignment> searchKeyAssignments(@RequestParam(required = false) Long keyCardId,
                                                    @RequestParam(required = false) Long employeeId,
                                                    @RequestParam(required = false) LocalDate startDate,
                                                    @RequestParam(required = false) LocalDate endDate) {
        return keyAssignmentService.searchKeyAssignments(keyCardId, employeeId, startDate, endDate);
    }

    @GetMapping("/{id}/act")
    public ResponseEntity<String> generateKeyAssignmentAct(@PathVariable Long id) {
        Optional<KeyAssignment> keyAssignment = keyAssignmentService.getKeyAssignmentById(id);
        if (keyAssignment.isPresent()) {
            KeyAssignment assignment = keyAssignment.get();
            String actContent = String.format(
                    "Акт приема-передачи ключа\n\nДата передачи: %s\nСотрудник: %s\nКарта-ключ: %s\n\nПодписи сторон:\n___________\n___________",
                    assignment.getAssignmentDate(),
                    assignment.getCertificate().getEmployee().getFullName(),
                    assignment.getKeyCard().getSerialNumber()
            );
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=act-" + id + ".txt")
                    .body(actContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

