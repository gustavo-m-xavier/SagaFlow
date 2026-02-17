package com.sagaflow.order_service.services;

import com.sagaflow.models.order.Orchestrator;
import com.sagaflow.models.topics.Topics;
import com.sagaflow.order_service.repository.OrchestratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrchestratorService {

    private OrchestratorRepository orchestratorRepository;

    public OrchestratorService(OrchestratorRepository orchestratorRepository) {
        this.orchestratorRepository = orchestratorRepository;
    }

    @Transactional
    public UUID saveOrchestrator(UUID orderId) {
        Orchestrator orchestrator = new Orchestrator();
        orchestrator.setOrderId(orderId);
        orchestrator.setStep("order_created");
        orchestratorRepository.save(orchestrator);
        return orchestrator.getId();
    }

    @Transactional
    public void updateOrchestratorStatus(UUID id, Topics status) {
        var orchestrator = orchestratorRepository.findById(id).orElseThrow(() -> new RuntimeException("Orchestrator not found"));
        orchestrator.setStep(status.getTopicName());
        orchestratorRepository.save(orchestrator);
    }

}
