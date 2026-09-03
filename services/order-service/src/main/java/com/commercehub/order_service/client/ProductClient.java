package com.commercehub.order_service.client;

import com.commercehub.order_service.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/v1/products/sku/{sku}")
    ProductResponse getProductBySku(
            @PathVariable String sku);
}