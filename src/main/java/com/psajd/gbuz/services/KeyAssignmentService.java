package com.psajd.gbuz.services;

import com.psajd.gbuz.entities.KeyAssignment;
import com.psajd.gbuz.repositories.KeyAssignmentRepository;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public ResponseEntity<byte[]> createDocx(Long id, KeyAssignment assignment, DateTimeFormatter formatter) {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Заголовок
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setText("АКТ ПРИЕМА-ПЕРЕДАЧИ");
            titleRun.addBreak();
            titleRun.setText("устройства JaCarta");
            titleRun.addBreak();

            // Дата документа
            XWPFParagraph dateParagraph = document.createParagraph();
            dateParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun dateRun = dateParagraph.createRun();
            dateRun.setText(assignment.getAssignmentDate().format(formatter));

            // Основной текст
            XWPFParagraph bodyParagraph = document.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun bodyRun = bodyParagraph.createRun();
            bodyRun.setFontSize(12);
            bodyRun.setText("Настоящим Актом подтверждается, что ГБУЗ АО «Коряжемская городская больница» передает устройство JaCarta для использования в программе МИС «Ариадна» в качестве средства электронной подписи для подписания электронных больничных.");
            bodyRun.addBreak();
            bodyRun.addBreak();
            bodyRun.setText("Пользователь подтверждает, что корпус переданного Устройства не имеет видимых признаков повреждения и взлома. Пользователь обязуется использовать и хранить JaCarta в соответствии с правилами эксплуатации и хранения JaCarta.");
            bodyRun.addBreak();
            bodyRun.setText("Пользователь обязуется не передавать JaCarta третьим лицам. Пользователь обязуется не использовать JaCarta как USB-флешку.");
            bodyRun.addBreak();
            bodyRun.setText("В случае утраты (хищения) или повреждения JaCarta Пользователь обязуется восстановить его за свой счет.");
            bodyRun.addBreak();
            bodyRun.addBreak();

            // Таблица
            XWPFTable table = document.createTable(2, 3);
            table.setWidth("100%");

            // Заголовки таблицы
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ФИО пользователя");
            headerRow.getCell(1).setText("Серийный номер");
            headerRow.getCell(2).setText("Подпись");

            // Данные таблицы
            XWPFTableRow dataRow = table.getRow(1);
            dataRow.getCell(0).setText(assignment.getCertificate().getEmployee().getFullName());
            dataRow.getCell(1).setText(assignment.getKeyCard().getSerialNumber());
            dataRow.getCell(2).setText("___________");

            // Сохранение документа
            document.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=act-" + id + ".docx")
                    .body(out.toByteArray());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
