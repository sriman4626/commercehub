package com.commercehub.product_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkProductRequest {

    @NotEmpty(message = "Product IDs cannot be empty")
    private List<Long> productIds;

}