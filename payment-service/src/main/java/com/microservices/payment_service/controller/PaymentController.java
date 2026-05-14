package com.microservices.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservices.payment_service.dto.PaymentRequest;
import com.microservices.payment_service.entity.Payment;
import com.microservices.payment_service.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/pay")
    public Payment pay(@RequestBody PaymentRequest request) {
        return service.processPayment(request.getMethod());
    }
}