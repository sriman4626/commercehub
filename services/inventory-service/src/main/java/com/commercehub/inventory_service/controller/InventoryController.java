package com.commercehub.inventory_service.controller;

import com.commercehub.inventory_service.dto.request.ReserveStockRequest;
import com.commercehub.inventory_service.dto.response.InventoryResponse;
import com.commercehub.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryResponse reserveStock(
            @Valid @RequestBody ReserveStockRequest request){
        return inventoryService.reserveStock(request);
    }
}
