CREATE TABLE users (

                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       auth_user_id BIGINT NOT NULL,

                       username VARCHAR(100) NOT NULL UNIQUE,

                       email VARCHAR(255) NOT NULL UNIQUE,

                       first_name VARCHAR(100),

                       last_name VARCHAR(100),

                       phone VARCHAR(20),

                       date_of_birth DATE,

                       gender VARCHAR(20),

                       status VARCHAR(20) NOT NULL,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);