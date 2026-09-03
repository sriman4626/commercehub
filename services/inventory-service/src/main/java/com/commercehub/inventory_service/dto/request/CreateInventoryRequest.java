package com.commercehub.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInventoryRequest {

    @NotBlank(message = "SKU is required")
    private String sku;
}
