package com.psajd.gbuz.services;

import com.psajd.gbuz.entities.KeyAssignment;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class DocumentService {

    public ResponseEntity<byte[]> keyAssignmentDocument(Long id, KeyAssignment assignment, DateTimeFormatter formatter) {
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

            // Дата
            XWPFParagraph dateParagraph = document.createParagraph();
            dateParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun dateRun = dateParagraph.createRun();
            dateRun.addBreak();
            dateRun.setBold(true);
            dateRun.setText(String.format("“%s” %s %s г.",
                    String.format("%02d", assignment.getAssignmentDate().getDayOfMonth()),
                    assignment.getAssignmentDate().getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru")),
                    assignment.getAssignmentDate().getYear()));
            dateRun.addBreak();
            dateRun.addBreak();
            dateRun.addBreak();

            // Основной текст
            XWPFParagraph bodyParagraph = document.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun bodyRun = bodyParagraph.createRun();
            bodyRun.setFontSize(12);
            bodyRun.setText("Настоящим Актом подтверждается, что ГБУЗ АО «Коряжемская городская больница» передает устройство JaCarta для использования ");
            XWPFRun boldTextRun = bodyParagraph.createRun();
            boldTextRun.setFontSize(12);
            boldTextRun.setBold(true);
            boldTextRun.setText("в программе МИС «Ариадна»");
            bodyRun = bodyParagraph.createRun();
            bodyRun.setFontSize(12);
            bodyRun.setText(" в качестве средства электронной подписи для подписания электронных больничных.");
            bodyRun.addBreak();
            bodyRun.addBreak();

            // Пункты обязательств
            bodyRun.setText("Пользователь подтверждает, что корпус переданного Устройства не имеет видимых признаков повреждения и взлома.");
            bodyRun.addBreak();
            bodyRun.setText("Пользователь обязуется использовать и хранить JaCarta в соответствии правилами эксплуатации и хранения JaCarta.");
            bodyRun.addBreak();
            bodyRun.addBreak();
            bodyRun.setText("Пользователь обязуется не передавать JaCarta третьим лицам.");
            bodyRun.addBreak();
            bodyRun.addBreak();
            bodyRun.setText("Пользователь обязуется не использовать JaCarta как USB-флешку.");
            bodyRun.addBreak();
            bodyRun.addBreak();
            bodyRun.setText("В случае утери (хищения) или повреждения JaCarta Пользователь обязуется восстановить его за свой счет.");
            bodyRun.addBreak();
            bodyRun.addBreak();

            // Таблица
            XWPFTable table = document.createTable(3, 3);
            table.setWidth("100%");

            XWPFTableRow headerRow = table.getRow(0);

            XWPFParagraph headerCellParagraph0 = headerRow.getCell(0).getParagraphs().get(0);
            XWPFRun headerCellRun0 = headerCellParagraph0.createRun();
            headerCellRun0.setBold(true);
            headerCellRun0.setText("ФИО пользователя");

            XWPFParagraph headerCellParagraph1 = headerRow.getCell(1).getParagraphs().get(0);
            XWPFRun headerCellRun1 = headerCellParagraph1.createRun();
            headerCellRun1.setBold(true);
            headerCellRun1.setText("Серийный номер");

            XWPFParagraph headerCellParagraph2 = headerRow.getCell(2).getParagraphs().get(0);
            XWPFRun headerCellRun2 = headerCellParagraph2.createRun();
            headerCellRun2.setBold(true);
            headerCellRun2.setText("Подпись");

            // Данные таблицы
            XWPFTableRow dataRow = table.getRow(1);
            dataRow.getCell(0).setText(assignment.getCertificate().getEmployee().getFullName());
            dataRow.getCell(1).setText(assignment.getKeyCard().getSerialNumber());
            dataRow.getCell(2).setText(""); // Оставляем пустым для подписи

            // Сохранение документа
            document.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=act-" + id + ".docx")
                    .body(out.toByteArray());
        } catch (IOException | RuntimeException e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
