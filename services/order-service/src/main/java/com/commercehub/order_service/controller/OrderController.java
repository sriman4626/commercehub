package com.commercehub.order_service.controller;

import com.commercehub.order_service.dto.request.CreateOrderRequest;
import com.commercehub.order_service.dto.response.OrderResponse;
import com.commercehub.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}