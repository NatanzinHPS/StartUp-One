-- Criar tabela users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(32),
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ROLE_PASSAGEIRO', 'ROLE_MOTORISTA', 'ROLE_ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

-- Criar tabela drivers
CREATE TABLE drivers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Criar tabela passengers
CREATE TABLE passengers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Criar tabela de relacionamento drivers_passengers
CREATE TABLE drivers_passengers (
    driver_id BIGINT NOT NULL,
    passenger_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (driver_id, passenger_id),
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    INDEX idx_driver_id (driver_id),
    INDEX idx_passenger_id (passenger_id)
);

-- Criar tabela address
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    street VARCHAR(255),
    number_street VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(2),
    cep VARCHAR(9),
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    INDEX idx_passenger_id (passenger_id)
);

-- Criar tabela passenger_schedules
CREATE TABLE passenger_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    day_of_week TINYINT NOT NULL COMMENT '1=Monday, 7=Sunday',
    schedule ENUM('MORNING', 'AFTERNOON', 'BOTH') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    INDEX idx_passenger_id (passenger_id),
    INDEX idx_day_of_week (day_of_week)
);

-- Criar tabela check_ins
CREATE TABLE check_ins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'PENDING') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,
    INDEX idx_passenger_date (passenger_id, date),
    INDEX idx_driver_date (driver_id, date),
    INDEX idx_date (date),
    UNIQUE KEY unique_checkin (passenger_id, driver_id, date)
);

-- Criar tabela payment_proofs
CREATE TABLE payment_proofs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    file_key VARCHAR(255) NOT NULL,
    amount DECIMAL(6,2) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,
    INDEX idx_passenger_id (passenger_id),
    INDEX idx_driver_id (driver_id),
    INDEX idx_status (status)
);