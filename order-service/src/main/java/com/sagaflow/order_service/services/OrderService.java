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

    OrderService(OrderProducer orderProducer, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(OrderRequest orderRequest) throws JsonProcessingException {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setProductId(orderRequest.productId());
        orderItem.setQuantity(orderRequest.quantity());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductRecord> response = restTemplate.exchange("http://localhost:8081/api/inventory/" + orderRequest.productId(), HttpMethod.GET, null, ProductRecord.class);
        ProductRecord product = response.getBody();
        orderItem.setUnitPrice(product.price());
        orderItemRepository.save(orderItem);

        orderProducer.sendOrderCreatedEvent(
                new OrderInfoRecord(
                        orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice()
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
