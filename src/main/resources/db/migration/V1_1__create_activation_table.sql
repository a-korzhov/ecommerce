CREATE TABLE IF NOT EXISTS activations
(
    id              BIGSERIAL PRIMARY KEY,
    activation_code VARCHAR(255) NOT NULL,
    user_id         BIGINT REFERENCES users (id)
);