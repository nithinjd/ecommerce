package com.example.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private PaymentDetails paymentDetails;
    private ProductDetails productDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public  static class ProductDetails{
        private long productId;
        private String productName;
        private long quantity;
        private long price;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails{
        private long paymentId;
        private PaymentMode paymentMode;
        private Instant paymentDate;
        private String paymentStatus;
    }
}
