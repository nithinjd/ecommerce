package com.example.PaymentService.service;

import com.example.PaymentService.entity.Payment;
import com.example.PaymentService.model.PaymentRequest;
import com.example.PaymentService.model.PaymentResponse;
import com.example.PaymentService.repository.TransactionDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Service
public class PaymentServiceImpl implements PaymentService{
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
@Autowired
private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public String doPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .paymentMode(paymentRequest.getPaymentMode() != null ? paymentRequest.getPaymentMode() : null)  // Check if PaymentMode is not null
                .paymentStatus("SUCCESS")
                .paymentDate(Instant.now())
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())  // Ensure amount is set
                .build();

        Payment savedPayment=transactionDetailsRepository.save(payment);
        log.info("Payment saved with ID: {} for order ID: {}", savedPayment.getId(), paymentRequest.getOrderId());
        return "Payment is successfully done";
    }

    @Override
    public PaymentResponse getPaymentDetails(long orderId) {
        log.info("Fetching payment details for order ID: {}", orderId);
        Payment payment = transactionDetailsRepository.findByOrderId(orderId);
        if (payment == null) {
            log.error("No payment found for order ID: {}", orderId);  // Log if payment is null
            throw new RuntimeException("Payment not found for order ID: " + orderId);  // Handle null case gracefully
        }
        PaymentResponse paymentResponse = new PaymentResponse();
        BeanUtils.copyProperties(payment , paymentResponse);
        return paymentResponse;
    }
}
