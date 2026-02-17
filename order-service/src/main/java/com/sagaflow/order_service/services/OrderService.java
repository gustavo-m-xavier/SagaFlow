package com.sagaflow.order_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sagaflow.models.inventory.ProductRecord;
import com.sagaflow.models.order.*;
import com.sagaflow.order_service.producer.OrderProducer;
import com.sagaflow.order_service.repository.OrderItemRepository;
import com.sagaflow.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderProducer orderProducer;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrchestratorService orchestratorService;

    OrderService(OrderProducer orderProducer, OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrchestratorService orchestratorService) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orchestratorService = orchestratorService;
    }

@Transactional(propagation = Propagation.REQUIRED)
public void createOrder(OrderRequest orderRequest) throws JsonProcessingException {
    Order order = createAndSaveOrder();
    OrderItem orderItem = createAndSaveOrderItem(order.getOrderId(), orderRequest);
    orchestratorService.saveOrchestrator(order.getOrderId());
    publishOrderCreatedEvent(orderItem);
}

private Order createAndSaveOrder() {
    Order order = new Order();
    order.setStatus(OrderStatus.CREATED);
    orderRepository.save(order);
    return order;
}

private OrderItem createAndSaveOrderItem(UUID orderId, OrderRequest orderRequest) {
    OrderItem orderItem = new OrderItem();
    orderItem.setOrderId(orderId);
    orderItem.setProductId(orderRequest.productId());
    orderItem.setQuantity(orderRequest.quantity());

    ProductRecord product = fetchProductDetails(orderRequest.productId());
    orderItem.setUnitPrice(product.price());

    orderItemRepository.save(orderItem);
    return orderItem;
}

private ProductRecord fetchProductDetails(UUID productId) {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<ProductRecord> response = restTemplate.exchange(
            "http://localhost:8081/api/inventory/" + productId,
            HttpMethod.GET,
            null,
            ProductRecord.class
    );
    return response.getBody();
}

private void publishOrderCreatedEvent(OrderItem orderItem) throws JsonProcessingException {
    orderProducer.sendOrderCreatedEvent(
            new OrderInfoRecord(
                    orderItem.getOrderId(),
                    orderItem.getProductId(),
                    orderItem.getQuantity(),
                    orderItem.getUnitPrice()
            )
    );
}

    public OrderInfoRecord getOrderInfoById(String orderId) {
        OrderItem orderItem = orderItemRepository
                .findByOrderId(UUID.fromString(orderId))
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatusCode.valueOf(404), "Order not found")
                );
        return new OrderInfoRecord(orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice());
    }
}
