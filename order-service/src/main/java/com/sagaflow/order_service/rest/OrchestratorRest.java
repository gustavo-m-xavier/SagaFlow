package com.sagaflow.order_service.rest;

import com.sagaflow.models.topics.Topics;
import com.sagaflow.order_service.services.OrchestratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orchestrators")
public class OrchestratorRest {
    OrchestratorService orchestratorService;

    public OrchestratorRest(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity updateOrchestratorStatus(@PathVariable("id") UUID id,
                                                   @RequestParam("status") Topics status) {
        orchestratorService.updateOrchestratorStatus(id, status);
        return ResponseEntity.accepted().build();
    }
}

