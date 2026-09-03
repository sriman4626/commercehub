package com.commercehub.product_service.dto.client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInventoryRequest {

    private String sku;
}