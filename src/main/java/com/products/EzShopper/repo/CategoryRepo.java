package com.products.EzShopper.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.products.EzShopper.model.Category;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
	
	Category findCategoryByName(String name);

	List<Category> findByIsActiveTrue();
}
