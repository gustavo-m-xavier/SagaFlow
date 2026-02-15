package com.sagaflow.models.order;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orquestrator", schema = "order_schema")
public class Orchestrator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "step")
    private String step;

    // Constructor (required by JPA)
    public Orchestrator() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}

