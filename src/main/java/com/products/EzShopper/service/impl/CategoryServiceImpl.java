package com.products.EzShopper.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.EzShopper.model.Category;
import com.products.EzShopper.repo.CategoryRepo;
import com.products.EzShopper.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public Category saveCategory(Category category) {
		
		return categoryRepo.save(category);
	}

	@Override
	public List<Category> getCategories() {
		
		return categoryRepo.findAll();
	}

	@Override
	public Category findCategoryByName(String name) {
		
		return categoryRepo.findCategoryByName(name);
	}

	@Override
	public boolean deleteCategory(String id) {
		Category category = categoryRepo.findById(id).orElse(null);
		
		if(category != null) {
			categoryRepo.deleteById(id);
			return true;
		}
		
		return false;
	}

	@Override
	public Category findCategoryById(String id) {
		Category category = categoryRepo.findById(id).orElse(null);
		
		return category;
	}

	@Override
	public Category updateCategory(Category category) {
		
		Category foundCategory = categoryRepo.findById(category.getId()).orElse(null);
		
		if(foundCategory != null) {
			return categoryRepo.save(category);
		}
		
		return null;
	}

	@Override
	public List<Category> getActiveCategories() {
		
		return categoryRepo.findByIsActiveTrue();
	}

}
