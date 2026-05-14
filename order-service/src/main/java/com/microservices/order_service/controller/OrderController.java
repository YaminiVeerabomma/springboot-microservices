package com.microservices.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservices.order_service.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/place/{productId}")
    public String placeOrder(@PathVariable Long productId) {
        return service.placeOrder(productId);
    }
}