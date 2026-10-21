package com.restaurant.dto;

import com.restaurant.model.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest(
        LocalDateTime orderDate,
        OrderStatus status,
        Long employeeId,
        @NotEmpty List<@Valid OrderItemRequest> items) {}
