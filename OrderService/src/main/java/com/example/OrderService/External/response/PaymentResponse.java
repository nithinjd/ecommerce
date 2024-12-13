package com.example.OrderService.External.response;

import com.example.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long id;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private double amount;
    private Instant paymentDate;
    private long orderId;
}
