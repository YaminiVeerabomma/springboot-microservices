package com.microservices.order_service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.order_service.entity.Order;
import com.microservices.order_service.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderService {

    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    private OrderRepository orderRepository;

    public String placeOrder(Long productId) {

        // 🔹 Step 1: Check Product
        String product;

        try {
            product = webClient.build()
                    .get()
                    .uri("http://PRODUCT-SERVICE/products/" + productId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return "Product Service is DOWN";
        }

        if (product == null || product.contains("not found")) {
            return "Product is Out of Stock";
        }

        // 🔹 Step 2: Payment with Circuit Breaker
        return processPayment(productId);
    }

    // 🔥 Circuit Breaker for Payment
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public String processPayment(Long productId) {

        Map<String, String> request = new HashMap<>();
        request.put("method", "UPI");

        String payment = webClient.build()
                .post()
                .uri("http://PAYMENT-SERVICE/payment/pay")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 🔴 Business Logic (NOT circuit breaker)
        if (payment.contains("FAILED")) {
            return "Payment Not Done";
        }

        // 🔹 Save Order
        Order order = new Order();
        order.setProductId(productId);
        order.setStatus("PLACED");

        orderRepository.save(order);

        return "Order Placed Successfully";
    }

    // 🔴 Only for SERVICE DOWN
    public String paymentFallback(Long productId, Exception e) {
        return "Payment Service is DOWN";
    }
}