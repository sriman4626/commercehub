package com.commercehub.product_service.dto.client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private String sku;
    private Integer availableQuantity;
    private Integer reservedQuantity;
}