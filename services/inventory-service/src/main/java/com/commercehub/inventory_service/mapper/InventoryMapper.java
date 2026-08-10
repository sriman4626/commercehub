package com.commercehub.inventory_service.mapper;

import com.commercehub.inventory_service.dto.response.InventoryResponse;
import com.commercehub.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryResponse toResponse(Inventory inventory);

}