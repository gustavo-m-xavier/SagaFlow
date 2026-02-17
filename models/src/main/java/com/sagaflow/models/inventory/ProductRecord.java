package com.sagaflow.models.inventory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public record ProductRecord(
        UUID id,
        String description,
        BigDecimal price,
        Integer availableQuantity
) {
}
