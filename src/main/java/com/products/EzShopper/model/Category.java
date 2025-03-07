package com.products.EzShopper.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "category")
public class Category {
	
	private String id;
	
	private String name;
	
	private String categoryImageData;
	
	private boolean isActive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryImageData() {
		return categoryImageData;
	}

	public void setCategoryImageData(String categoryImage) {
		this.categoryImageData = categoryImage;
	}

	public Category(String id, String name, String categoryImageData, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.categoryImageData = categoryImageData;
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
