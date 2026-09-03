package com.commercehub.order_service.dto.response;

import com.commercehub.order_service.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private List<OrderItemResponse> items;
}