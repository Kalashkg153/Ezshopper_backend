package com.products.EzShopper.model;

public class LoginResponse {

	private String username;
	private String role;
	private String token;
	private String message;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LoginResponse(String username, String role, String token, String message) {
		super();
		this.username = username;
		this.role = role;
		this.token = token;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
