package com.sagaflow.order_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagaflow.models.order.OrderInfoRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void sendOrderCreatedEvent(OrderInfoRecord orderInfoRecord) throws JsonProcessingException {
        String topic = "order-created";
        kafkaTemplate.send(topic, mapper.writeValueAsString(orderInfoRecord));
    }

}
