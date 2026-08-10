package com.commercehub.inventory_service.dto.response;

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