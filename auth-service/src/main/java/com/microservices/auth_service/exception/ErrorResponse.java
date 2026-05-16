package com.microservices.auth_service.exception;
import java.time.LocalDateTime;


public class ErrorResponse {
	
	private String message;
	private Long Status;
	private LocalDateTime time;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getStatus() {
		return Status;
	}
	public void setStatus(Long status) {
		Status = status;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
	

}
