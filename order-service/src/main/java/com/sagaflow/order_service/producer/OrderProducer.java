package com.sagaflow.order_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(String orderId) {
        String topic = "order-created";
        kafkaTemplate.send(topic, orderId);
        System.out.println("Sent order created event for order ID: " + orderId);
    }

}
