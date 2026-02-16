package com.sagaflow.inventory_service.repository;

import com.sagaflow.models.inventory.Inventory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InventoryRepository extends CrudRepository<Inventory, UUID> {

    @Modifying
    @Query("update Inventory i set i.availableQuantity = i.availableQuantity - :quantity where i.productId = :id and i.availableQuantity >= :quantity")
    Integer reduceStock(UUID id, Integer quantity);

}
