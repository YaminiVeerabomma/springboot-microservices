package com.microservices.auth_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import com.microservices.auth_service.Enum.*;

@Data
public class RegisterRequest {
	
	public String  name;
	public String email;
	public String password;
	@Enumerated(EnumType.STRING)
	public role role;

}
