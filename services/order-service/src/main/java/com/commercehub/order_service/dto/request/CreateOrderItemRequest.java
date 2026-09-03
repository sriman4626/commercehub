package com.commercehub.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderItemRequest {

    @NotBlank
    private String sku;

    @NotNull
    @Positive
    private Integer quantity;
}