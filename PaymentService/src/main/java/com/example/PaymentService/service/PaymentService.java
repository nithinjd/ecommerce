package com.example.PaymentService.service;

import com.example.PaymentService.model.PaymentRequest;
import com.example.PaymentService.model.PaymentResponse;

public interface PaymentService {
    String doPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentDetails(long orderId);
}
