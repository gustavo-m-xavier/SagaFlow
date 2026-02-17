package com.sagaflow.inventory_service.rest;


import com.sagaflow.inventory_service.services.InventoryService;
import com.sagaflow.models.inventory.ProductRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryRest {

    private final InventoryService inventoryService;

    public InventoryRest(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRecord> getProductById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(inventoryService.getProductById(id));
    }
}
