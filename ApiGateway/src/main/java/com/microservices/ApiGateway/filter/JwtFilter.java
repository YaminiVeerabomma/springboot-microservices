package com.microservices.apigateway.filter;


import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.microservices.apigateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 🔓 Allow authentication APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // 🔴 Get Authorization Header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        // 🔴 Check if header is present
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 🔴 Extract token
        String token = authHeader.substring(7);

        try {
            // 🔥 Validate and parse token
            Claims claims = JwtUtil.getClaims(token);

            String role = claims.get("role", String.class);

            // 🔐 ROLE-BASED AUTHORIZATION

            // Only PRODUCER can add product
            if (path.startsWith("/products/add") && !"PRODUCER".equals(role)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // Only ADMIN can access admin APIs
            if (path.startsWith("/admin") && !"ADMIN".equals(role)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // USER, PRODUCER, ADMIN can place orders (no restriction here)

        } catch (Exception e) {
            // 🔴 Invalid or expired token
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // ✅ Continue request if everything is valid
        return chain.filter(exchange);
    }
}