CREATE TABLE orders
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    order_number VARCHAR(50) NOT NULL UNIQUE,

    customer_id BIGINT NOT NULL,

    total_amount DECIMAL(19, 2) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_items
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    order_id BIGINT NOT NULL,

    sku VARCHAR(100) NOT NULL,

    product_name VARCHAR(255) NOT NULL,

    unit_price DECIMAL(19, 2) NOT NULL,

    quantity INT NOT NULL,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
            REFERENCES orders(id)
            ON DELETE CASCADE
);