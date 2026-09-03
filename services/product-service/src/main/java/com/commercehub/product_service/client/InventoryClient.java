package com.commercehub.product_service.client;

import com.commercehub.product_service.dto.client.CreateInventoryRequest;
import com.commercehub.product_service.dto.client.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/api/v1/inventory")
    InventoryResponse createInventory(
            @RequestBody CreateInventoryRequest request);

    @GetMapping("/api/v1/inventory/{sku}")
    InventoryResponse getInventory(@PathVariable String sku);
}
