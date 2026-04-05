package com.sagaflow.payment_service.repository;

import com.sagaflow.models.payment.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID>{
}