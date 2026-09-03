package com.commercehub.order_service.client;

import com.commercehub.order_service.client.dto.InventoryResponse;
import com.commercehub.order_service.client.dto.ReleaseStockRequest;
import com.commercehub.order_service.client.dto.ReserveStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/v1/inventory/reserve")
    InventoryResponse reserveStock(@RequestBody ReserveStockRequest request);

    @PostMapping("/api/v1/inventory/release")
    InventoryResponse releaseStock(@RequestBody ReleaseStockRequest request);
}