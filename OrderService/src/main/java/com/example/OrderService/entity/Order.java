package com.example.OrderService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "customer_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="product_id")
    private long productId;

    @Column(name="order_date")
    private Instant orderDate;

    @Column(name="order_status")
    private String orderStatus;

    @Column(name="quantity")
    private int quantity;

    @Column(name="amount")
    private long amount;
}
