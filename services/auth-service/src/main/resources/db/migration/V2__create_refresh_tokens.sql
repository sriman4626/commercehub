CREATE TABLE refresh_tokens (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                token VARCHAR(512) NOT NULL UNIQUE,
                                user_id BIGINT NOT NULL,
                                expiry_date DATETIME NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT FALSE,
                                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_refresh_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE
);