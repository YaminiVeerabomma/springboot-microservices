package com.microservices.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.payment_service.entity.Payment;
import com.microservices.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    public Payment processPayment(String method) {

        Payment payment = new Payment();
        payment.setMethod(method);

        if (method.equalsIgnoreCase("UPI") || method.equalsIgnoreCase("NetBanking")) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        return repo.save(payment);
    }
}
