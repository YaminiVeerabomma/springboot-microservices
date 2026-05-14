package com.microservices.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.order_service.entity.Order;
import com.microservices.order_service.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClient;


        public String placeOrder(Long productId) {

            String productResponse;

            // 🔹 Step 1: Check Product
            try {
                productResponse = webClient.build()
                        .get()
                        .uri("http://PRODUCT-SERVICE/products/" + productId)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (Exception e) {
                return "Out of Stock";  // ✅ your requirement
            }

            if (productResponse == null || productResponse.isEmpty()) {
                return "Out of Stock";
            }

            // 🔹 Step 2: Payment
            String paymentResponse;

            try {
                paymentResponse = webClient.build()
                        .post()
                        .uri("http://PAYMENT-SERVICE/payment/pay")
                        .bodyValue("{\"method\":\"UPI\"}")
                        .header("Content-Type", "application/json")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (Exception e) {
                return "Payment Failed";
            }

            if (paymentResponse == null || !paymentResponse.toLowerCase().contains("success")) {
                return "Payment Failed";
            }

            // 🔹 Step 3: Save Order
            Order order = new Order();
            order.setProductId(productId);
            order.setStatus("PLACED");

            orderRepository.save(order);

            return "Order Success";
        }
    }