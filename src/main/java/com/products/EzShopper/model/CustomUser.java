package com.products.EzShopper.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class CustomUser {
	
	private String id;
	
	private String fullName;
	
	private String email;
	
	private String password;
	
	private String contact;
	
	private String address;
	
	private String pincode;
	
	private String role;
	
	private String resetPasswordToken;
	
	private Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public CustomUser(String id, String fullName, String email, String password, String contact, String address,
			String pincode, String role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.address = address;
		this.pincode = pincode;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resePasswordToken) {
		this.resetPasswordToken = resePasswordToken;
	}
	
	
}
