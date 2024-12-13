package com.example.OrderService.External.request;

import com.example.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long orderId;
    private PaymentMode paymentMode;
    private double amount;
    private String referenceNumber;
}
