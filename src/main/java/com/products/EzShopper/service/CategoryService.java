package com.products.EzShopper.service;

import java.util.List;

import com.products.EzShopper.model.Category;

public interface CategoryService {
	
	public Category findCategoryByName(String name);
	public Category saveCategory(Category category);
	public List<Category> getCategories();
	public boolean deleteCategory(String id);
	public Category findCategoryById(String id);
	public Category updateCategory(Category category);
	public List<Category> getActiveCategories();
	
	
}
