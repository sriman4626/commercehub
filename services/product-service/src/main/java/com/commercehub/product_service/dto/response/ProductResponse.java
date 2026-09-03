package com.commercehub.product_service.dto.response;

import com.commercehub.product_service.constant.ProductStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

    private ProductStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer availableQuantity;

    private Integer reservedQuantity;

}