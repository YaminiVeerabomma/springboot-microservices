package com.microservices.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.auth_service.dto.LoginRequest;
import com.microservices.auth_service.dto.RegisterRequest;
import com.microservices.auth_service.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest req) {
		return service.register(req);
	}
	
	
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest req) {
		return service.login(req);
	}
	
	
	
	
}
