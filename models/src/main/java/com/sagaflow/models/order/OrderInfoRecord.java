package com.sagaflow.models.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderInfoRecord(
        UUID orderId,
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
