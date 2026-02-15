package com.sagaflow.order_service.services;

import com.sagaflow.models.order.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void createOrder(OrderRequest orderRequest) {
        System.out.println("Creating order for product ID: " + orderRequest.productId() + " with quantity: " + orderRequest.quantity());
    }
}
