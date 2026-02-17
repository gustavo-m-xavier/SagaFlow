package com.sagaflow.inventory_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagaflow.inventory_service.services.InventoryService;
import com.sagaflow.models.order.OrderInfoRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private InventoryService inventoryService;
    private final ObjectMapper mapper;

    public InventoryConsumer(InventoryService inventoryService, ObjectMapper mapper) {
        this.inventoryService = inventoryService;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "order-created")
    public void consumeOrderCreatedEvent(String orderInfoString) throws JsonProcessingException {
        OrderInfoRecord orderInfoRecord = mapper.readValue(orderInfoString, OrderInfoRecord.class);
        System.out.println("Received order created event for order ID: " + orderInfoRecord.orderId());
        inventoryService.updateInventory(orderInfoRecord);
    }
}
