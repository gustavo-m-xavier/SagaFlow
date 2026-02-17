package com.sagaflow.order_service.repository;

import com.sagaflow.models.order.Orchestrator;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrchestratorRepository extends CrudRepository<Orchestrator, UUID> {
}
