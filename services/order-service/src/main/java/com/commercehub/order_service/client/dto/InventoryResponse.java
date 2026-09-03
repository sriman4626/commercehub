package com.commercehub.order_service.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private String sku;

    private Integer availableQuantity;

    private Integer reservedQuantity;
}