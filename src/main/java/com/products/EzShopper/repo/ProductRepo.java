package com.products.EzShopper.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.products.EzShopper.model.ImageDataDTO;
import com.products.EzShopper.model.Product;


@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
	
	Product findProductByTitle(String title);

	List<Product> findByIsTrendingTrue();

	List<Product> findByIsLatestProductTrue();

	List<Product> findByCategory(String category);
	
	@Query(value = "{ '_id': ?0 }", fields = "{ 'imageData': 1, '_id': 0 }")
    Optional<ImageDataDTO> findImageDataById(String productId);
}
