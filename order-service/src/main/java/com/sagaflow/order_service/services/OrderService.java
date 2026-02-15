package com.sagaflow.order_service.services;

import com.sagaflow.models.order.Order;
import com.sagaflow.models.order.OrderRequest;
import com.sagaflow.models.order.OrderStatus;
import com.sagaflow.order_service.producer.OrderProducer;
import com.sagaflow.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderProducer orderProducer;
    private OrderRepository orderRepository;

    OrderService(OrderProducer orderProducer, OrderRepository orderRepository) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(new BigDecimal("1000.0"));
        orderRepository.save(order);
//        orderProducer.sendOrderCreatedEvent(orderRequest.productId().toString());
//        System.out.println("Creating order for product ID: " + orderRequest.productId() + " with quantity: " + orderRequest.quantity());
    }
}
