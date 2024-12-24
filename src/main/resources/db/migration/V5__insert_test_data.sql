-- Добавляем сотрудников
INSERT INTO employees (full_name, position)
VALUES ('Иван Иванов', 'Программист'),
       ('Петр Петров', 'Аналитик'),
       ('Мария Сидорова', 'Тестировщик');

-- Добавляем карты-ключи
INSERT INTO key_cards (serial_number)
VALUES ('A1B2C3D4'),
       ('E5F6G7H8'),
       ('I9J0K1L2');

-- Добавляем сертификаты
INSERT INTO certificates (employee_id, issue_date, expiration_date, password)
VALUES (1, '2024-12-01', '2025-12-01', 'password123'),
       (2, '2024-12-02', '2025-12-02', 'securepass456'),
       (3, '2024-12-03', '2025-12-03', 'key789');

-- Добавляем выдачу ключей
INSERT INTO key_issuances (issuance_date, certificate_id, key_card_id)
VALUES ('2024-12-10', 1, 1),
       ('2024-12-11', 2, 2),
       ('2024-12-12', 3, 3);
