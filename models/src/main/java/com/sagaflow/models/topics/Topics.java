package com.sagaflow.models.topics;

public enum Topics {
    ORDER_CREATED("order-created"),
    INVENTORY_RESERVED("inventory-reserved"),
    INVENTORY_RESERVATION_FAILED("inventory-reservation-failed"),
    PAYMENT_PENDING("payment-pending"),
    PAYMENT_COMPLETED("payment-completed"),
    PAYMENT_FAILED("payment-failed"),
    ORDER_CONFIRMED("order-confirmed"),
    ORDER_CANCELLED("order-cancelled");

    private final String topicName;

    Topics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
