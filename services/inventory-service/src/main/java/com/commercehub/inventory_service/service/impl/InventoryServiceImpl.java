package com.commercehub.inventory_service.service.impl;

import com.commercehub.inventory_service.dto.request.ReserveStockRequest;
import com.commercehub.inventory_service.dto.response.InventoryResponse;
import com.commercehub.inventory_service.entity.Inventory;
import com.commercehub.inventory_service.exception.InsufficientStockException;
import com.commercehub.inventory_service.exception.ResourceNotFoundException;
import com.commercehub.inventory_service.mapper.InventoryMapper;
import com.commercehub.inventory_service.repository.InventoryRepository;
import com.commercehub.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public InventoryResponse reserveStock(ReserveStockRequest request) {
        Inventory inventory = inventoryRepository.findBySku(request.getSku())
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Inventory not found for SKU: " + request.getSku()));

        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock for SKU: " + request.getSku());
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()-request.getQuantity());

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + request.getQuantity());

        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toResponse(savedInventory);
    }
}
