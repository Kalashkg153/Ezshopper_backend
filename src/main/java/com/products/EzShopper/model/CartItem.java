package com.products.EzShopper.model;

public class CartItem {
	
	private String productId;
	private String productImage;
    private String productName;
    private int quantity;
    private double price;
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public CartItem(String productId, String productImage, String productName, int quantity, double price) {
		super();
		this.productId = productId;
		this.productImage = productImage;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	
	
}	


