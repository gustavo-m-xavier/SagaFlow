package com.sagaflow.models.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(
        @NotNull(message = "Product ID cannot be null")
        UUID productId,

        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Total amount cannot be null")
        @Min(value = 0, message = "Total amount must be positive")
        BigDecimal totalAmount
) {
}
