CREATE TABLE enderecos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passageiro_id BIGINT NOT NULL,
    cep VARCHAR(9) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    is_principal BOOLEAN DEFAULT FALSE,
    apelido VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passageiro_id) REFERENCES passageiros(id) ON DELETE CASCADE,
    INDEX idx_passageiro_id (passageiro_id),
    INDEX idx_is_principal (is_principal)
);

ALTER TABLE passageiros DROP COLUMN endereco;
ALTER TABLE passageiros DROP COLUMN latitude;
ALTER TABLE passageiros DROP COLUMN longitude;
