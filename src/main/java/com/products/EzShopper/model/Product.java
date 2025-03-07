package com.products.EzShopper.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

	private String id;
	
	private String title;
	
	private String description;
	
	private String imageData;
	
	private double price;
	
	private int stock;
	
	private String category;
	
	private int discount;
	
	private double discountedPrice;
	
	private boolean isTrending;
	
	private boolean isLatestProduct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Product(String id, String title, String description, String imageData, double price, int stock,
			String category, int discount, double discountedPrice) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageData = imageData;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.discount = discount;
		this.discountedPrice = discountedPrice;
	}

	public boolean isTrending() {
		return isTrending;
	}

	public void setTrending(boolean isTrending) {
		this.isTrending = isTrending;
	}

	public boolean isLatestProduct() {
		return isLatestProduct;
	}

	public void setLatestProduct(boolean isLatestProduct) {
		this.isLatestProduct = isLatestProduct;
	}

	
	
	
}
