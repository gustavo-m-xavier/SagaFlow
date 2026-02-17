package com.sagaflow.order_service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sagaflow.models.order.OrderRequest;
import com.sagaflow.order_service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderRest {
    OrderService orderService;
    public OrderRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        orderService.createOrder(orderRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getOrderInfoById(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderService.getOrderInfoById(orderId));
    }
}
