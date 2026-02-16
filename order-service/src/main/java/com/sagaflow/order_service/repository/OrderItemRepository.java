package com.sagaflow.order_service.repository;


import com.sagaflow.models.order.Order;
import com.sagaflow.models.order.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, UUID> {

    Optional<OrderItem> findByOrderId(UUID orderId);
}
