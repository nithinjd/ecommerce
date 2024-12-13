package com.example.PaymentService.repository;

import com.example.PaymentService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<Payment,Long> {
    Payment findByOrderId(long orderId);
}
