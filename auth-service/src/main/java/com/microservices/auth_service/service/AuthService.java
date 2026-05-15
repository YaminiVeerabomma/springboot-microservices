package com.microservices.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.auth_service.dto.LoginRequest;
import com.microservices.auth_service.dto.RegisterRequest;
import com.microservices.auth_service.entity.User;
import com.microservices.auth_service.repository.UserRepository;
import com.microservices.auth_service.util.JwtUtil;





@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterRequest req) {
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(req.password);
        user.setRole(req.role);

        userRepository.save(user);

        return "User Registered";
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(req.password)) {
            throw new RuntimeException("Invalid password");
        }

        return JwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}