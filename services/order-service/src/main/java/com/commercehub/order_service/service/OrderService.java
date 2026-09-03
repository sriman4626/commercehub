package com.commercehub.order_service.service;

import com.commercehub.order_service.dto.request.CreateOrderRequest;
import com.commercehub.order_service.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrder(Long id);
}