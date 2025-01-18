CREATE TABLE key_assignment
(
    id             SERIAL PRIMARY KEY,
    assignment_date  DATE   NOT NULL,
    certificate_id BIGINT NOT NULL REFERENCES certificate (id) ON DELETE CASCADE,
    key_card_id    BIGINT NOT NULL REFERENCES key_card (id) ON DELETE CASCADE
);
