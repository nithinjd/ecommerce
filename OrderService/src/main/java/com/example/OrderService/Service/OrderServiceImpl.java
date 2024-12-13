package com.example.OrderService.Service;

import com.example.OrderService.External.client.PaymentService;
import com.example.OrderService.External.client.ProductService;
import com.example.OrderService.External.request.PaymentRequest;
import com.example.OrderService.External.response.PaymentResponse;
import com.example.OrderService.External.response.ProductResponse;
import com.example.OrderService.entity.Order;
import com.example.OrderService.exception.CustomException;
import com.example.OrderService.model.OrderRequest;
import com.example.OrderService.model.OrderResponse;
import com.example.OrderService.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {
public static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public ProductService productService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("The order is placed"+orderRequest);
        productService.reducedQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        Order order = Order.builder().productId(orderRequest.getProductId()).orderDate(Instant.now()).
                quantity(orderRequest.getQuantity()).amount(orderRequest.getAmount()).build();

        order = orderRepository.save(order);

        log.info("going to the payment service");
        String orderStatus;

        PaymentRequest paymentRequest = PaymentRequest.builder().orderId(order.getId()).
                paymentMode(orderRequest.getPaymentMode()).amount(orderRequest.getAmount()).
                referenceNumber(orderRequest.getReferenceNumber()).build();

        try{
            paymentService.doPayment(paymentRequest);
            log.info("payment is done");
            orderStatus = "PAYMENT_DONE";
        }catch (Exception e){
            log.info("payment is failed");
            orderStatus ="PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed successfully with order id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetailsById(long orderId) {

        log.info("Fetching order details for order id: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new CustomException("The" +
                "order with id: "+orderId+"is not found","NOT_FOUND",404));

        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCTSERVICE/products/"+order.getProductId(),ProductResponse.class);
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENTSERVICE/payments/"+order.getId(),PaymentResponse.class);

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder().productId(productResponse.getId()).
                productName(productResponse.getProductName()).price(productResponse.getPrice()).quantity(productResponse.getQuantity()).build();

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder().paymentId(paymentResponse.getId()).
                paymentMode(paymentResponse.getPaymentMode()).paymentStatus(paymentResponse.getPaymentStatus()).paymentDate(paymentResponse.getPaymentDate()).build();

        return OrderResponse.builder().orderId(order.getId()).orderDate(order.getOrderDate()).
                orderStatus(order.getOrderStatus()).amount(order.getAmount()).productDetails(productDetails).paymentDetails(paymentDetails).build();
    }
}
