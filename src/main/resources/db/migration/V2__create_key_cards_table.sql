CREATE TABLE key_card
(
    id            SERIAL PRIMARY KEY,
    serial_number CHAR(8) NOT NULL UNIQUE
);
