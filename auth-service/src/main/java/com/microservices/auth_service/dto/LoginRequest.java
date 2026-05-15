package com.microservices.auth_service.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String password;
}
