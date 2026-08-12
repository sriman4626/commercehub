package com.commercehub.inventory_service.controller;

import com.commercehub.inventory_service.dto.request.ReserveStockRequest;
import com.commercehub.inventory_service.dto.request.UpdateStockRequest;
import com.commercehub.inventory_service.dto.response.InventoryResponse;
import com.commercehub.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/reserve")
    public InventoryResponse reserveStock(
            @Valid @RequestBody ReserveStockRequest request){
        return inventoryService.reserveStock(request);
    }

    @PatchMapping("/stock")
    public InventoryResponse updateStock(
            @Valid @RequestBody UpdateStockRequest request) {

        return inventoryService.updateStock(request);
    }

    @PostMapping("release")
    public InventoryResponse releaseStock(
            @Valid @RequestBody ReserveStockRequest request){

        return inventoryService.releaseStock(request);
    }

    @GetMapping("/{sku}")
    public InventoryResponse getInventory(@PathVariable String sku) {
        return inventoryService.getInventory(sku);
    }


}
