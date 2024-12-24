CREATE TABLE certificates
(
    id              SERIAL PRIMARY KEY,
    employee_id     BIGINT       NOT NULL REFERENCES employees (id) ON DELETE CASCADE,
    issue_date      DATE         NOT NULL,
    expiration_date DATE         NOT NULL,
    password        VARCHAR(255) NOT NULL
);
