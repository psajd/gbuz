CREATE TABLE key_cards
(
    id            SERIAL PRIMARY KEY,
    serial_number CHAR(8) NOT NULL UNIQUE
);
