package com.sagaflow.inventory_service.services;


import com.sagaflow.inventory_service.repository.InventoryRepository;
import com.sagaflow.models.inventory.Inventory;
import com.sagaflow.models.inventory.ProductRecord;
import com.sagaflow.models.order.OrderInfoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class InventoryService {

    private Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void updateInventory(OrderInfoRecord orderInfoRecord) {
        log.info("Updating inventory based on order created event...");
        Integer rowsUpdated = inventoryRepository.reduceStock(orderInfoRecord.productId(), orderInfoRecord.quantity());
        // TODO enviar para outro tópico

        if (rowsUpdated > 0) {
            log.info("Updated stock for order ID: " + orderInfoRecord.orderId());
        } else {
            log.info("No stock for order ID: " + orderInfoRecord.orderId());
        }

    }

    public ProductRecord getProductById(UUID id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatusCode.valueOf(404), "Product not found")
        );
        return new ProductRecord(
                inventory.getProductId(),
                inventory.getDescription(),
                inventory.getPrice(),
                inventory.getAvailableQuantity()
        );

    }
}
