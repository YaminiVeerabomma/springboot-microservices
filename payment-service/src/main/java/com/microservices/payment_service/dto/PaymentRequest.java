package com.microservices.payment_service.dto;

public class PaymentRequest {

    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}