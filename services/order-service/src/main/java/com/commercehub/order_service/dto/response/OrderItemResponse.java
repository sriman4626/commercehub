package com.commercehub.order_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private String sku;

    private String productName;

    private BigDecimal unitPrice;

    private Integer quantity;
}