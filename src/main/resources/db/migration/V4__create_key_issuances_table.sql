CREATE TABLE key_issuances
(
    id             SERIAL PRIMARY KEY,
    issuance_date  DATE   NOT NULL,
    certificate_id BIGINT NOT NULL REFERENCES certificates (id) ON DELETE CASCADE,
    key_card_id    BIGINT NOT NULL REFERENCES key_cards (id) ON DELETE CASCADE
);
