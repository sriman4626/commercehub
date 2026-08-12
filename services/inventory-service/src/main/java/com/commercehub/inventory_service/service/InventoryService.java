package com.commercehub.inventory_service.service;

import com.commercehub.inventory_service.dto.request.ReserveStockRequest;
import com.commercehub.inventory_service.dto.request.UpdateStockRequest;
import com.commercehub.inventory_service.dto.response.InventoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {

    InventoryResponse reserveStock(ReserveStockRequest request);

    InventoryResponse updateStock(UpdateStockRequest request);

    InventoryResponse releaseStock(ReserveStockRequest request);

    InventoryResponse getInventory(String sku);


}
