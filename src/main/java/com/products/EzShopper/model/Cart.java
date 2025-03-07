package com.products.EzShopper.model;

import java.util.List;

public class Cart {
	private List<CartItem> items;
	private double totalPrice;
	
	
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Cart(){
		this.totalPrice = 0.0;
	}
	
	
}
