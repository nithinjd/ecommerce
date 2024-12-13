package com.example.OrderService.External.client;

import com.example.OrderService.External.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PAYMENTSERVICE")
public interface PaymentService {
    @PostMapping("/payments")
    ResponseEntity<String> doPayment(@RequestBody PaymentRequest paymentRequest);
}
