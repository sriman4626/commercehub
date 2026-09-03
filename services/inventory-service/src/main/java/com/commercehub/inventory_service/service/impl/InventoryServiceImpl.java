package com.commercehub.inventory_service.service.impl;

import com.commercehub.inventory_service.dto.request.CreateInventoryRequest;
import com.commercehub.inventory_service.dto.request.ReserveStockRequest;
import com.commercehub.inventory_service.dto.request.UpdateStockRequest;
import com.commercehub.inventory_service.dto.response.InventoryResponse;
import com.commercehub.inventory_service.entity.Inventory;
import com.commercehub.inventory_service.exception.DuplicateResourceException;
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

    @Override
    @Transactional
    public InventoryResponse updateStock(UpdateStockRequest request) {
        Inventory inventory = inventoryRepository.findBySku(request.getSku())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found for SKU: " + request.getSku()));

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + request.getQuantity());

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional
    public InventoryResponse releaseStock(ReserveStockRequest request) {
        Inventory inventory = inventoryRepository.findBySku(request.getSku())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found for SKU : " + request.getSku()));

        if(inventory.getReservedQuantity()<request.getQuantity()){
            throw new IllegalArgumentException("cannot release more stock than reserved");
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity()-request.getQuantity());

        inventory.setAvailableQuantity(inventory.getAvailableQuantity()+request.getQuantity());

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventory(String sku) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found for SKU: " + sku));

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) {
        if (inventoryRepository.findBySku(request.getSku()).isPresent()) {
            throw new DuplicateResourceException(
                    "Inventory already exists for SKU: " + request.getSku());
        }

        Inventory inventory = Inventory.builder()
                .sku(request.getSku())
                .availableQuantity(0)
                .reservedQuantity(0)
                .build();

        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(savedInventory);
    }


}
