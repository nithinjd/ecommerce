package com.example.OrderService.Service;

import com.example.OrderService.model.OrderRequest;
import com.example.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetailsById(long orderId);

}
