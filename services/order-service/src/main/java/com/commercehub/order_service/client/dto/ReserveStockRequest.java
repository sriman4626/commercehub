package com.commercehub.order_service.client.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveStockRequest {

    private String sku;

    private Integer quantity;
}