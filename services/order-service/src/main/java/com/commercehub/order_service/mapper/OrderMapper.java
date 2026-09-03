package com.commercehub.order_service.mapper;

import com.commercehub.order_service.dto.response.OrderItemResponse;
import com.commercehub.order_service.dto.response.OrderResponse;
import com.commercehub.order_service.entity.Order;
import com.commercehub.order_service.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    OrderItemResponse toItemResponse(OrderItem item);
}