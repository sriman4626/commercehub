CREATE TABLE products (

                          id BIGINT AUTO_INCREMENT PRIMARY KEY,

                          sku VARCHAR(50) NOT NULL UNIQUE,

                          name VARCHAR(255) NOT NULL,

                          description TEXT,

                          price DECIMAL(12,2) NOT NULL,

                          category_id BIGINT NOT NULL,

                          status VARCHAR(20) NOT NULL,

                          created_at TIMESTAMP NOT NULL,

                          updated_at TIMESTAMP NOT NULL

);