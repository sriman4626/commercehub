package com.commercehub.order_service.service.impl;

import com.commercehub.order_service.client.InventoryClient;
import com.commercehub.order_service.client.ProductClient;
import com.commercehub.order_service.client.dto.ProductResponse;
import com.commercehub.order_service.client.dto.ReleaseStockRequest;
import com.commercehub.order_service.client.dto.ReserveStockRequest;
import com.commercehub.order_service.dto.request.CreateOrderItemRequest;
import com.commercehub.order_service.dto.request.CreateOrderRequest;
import com.commercehub.order_service.dto.response.OrderResponse;
import com.commercehub.order_service.entity.Order;
import com.commercehub.order_service.entity.OrderItem;
import com.commercehub.order_service.entity.OrderStatus;
import com.commercehub.order_service.exception.ResourceNotFoundException;
import com.commercehub.order_service.mapper.OrderMapper;
import com.commercehub.order_service.repository.OrderRepository;
import com.commercehub.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .customerId(request.getCustomerId())
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<ReserveStockRequest> reservedItems = new ArrayList<>();

        try {
            for (CreateOrderItemRequest itemRequest : request.getItems()) {

                // 1. Get product information
                ProductResponse product =
                        productClient.getProductBySku(itemRequest.getSku());

                // 2. Reserve inventory
                ReserveStockRequest reserveRequest = ReserveStockRequest.builder()
                                .sku(itemRequest.getSku())
                                .quantity(itemRequest.getQuantity())
                                .build();

                inventoryClient.reserveStock(reserveRequest);

                reservedItems.add(reserveRequest);
                // 3. Create order item snapshot
                BigDecimal itemTotal = product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(itemRequest.getQuantity())
                        );

                OrderItem orderItem = OrderItem.builder()
                        .order(order)
                        .sku(product.getSku())
                        .productName(product.getName())
                        .unitPrice(product.getPrice())
                        .quantity(itemRequest.getQuantity())
                        .build();

                order.getItems().add(orderItem);

                totalAmount = totalAmount.add(itemTotal);
            }

            order.setTotalAmount(totalAmount);
            order.setStatus(OrderStatus.CONFIRMED);


            Order savedOrder = orderRepository.save(order);

            return orderMapper.toResponse(savedOrder);
        }
        catch (RuntimeException e) {

            // Compensation
            for (ReserveStockRequest reservedItem : reservedItems) {

                inventoryClient.releaseStock(
                        ReleaseStockRequest.builder()
                                .sku(reservedItem.getSku())
                                .quantity(reservedItem.getQuantity())
                                .build()
                );
            }

            throw e;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        return orderMapper.toResponse(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}