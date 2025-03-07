package com.products.EzShopper.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.EzShopper.exception.ProductNotFoundException;
import com.products.EzShopper.model.ImageDataDTO;
import com.products.EzShopper.model.Product;
import com.products.EzShopper.repo.ProductRepo;
import com.products.EzShopper.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepo productRepo;

	@Override
	public Product saveProduct(Product product) {
		
		return productRepo.save(product);
	}

	@Override
	public Product findProductByTitle(String title) {
		
		return productRepo.findProductByTitle(title);
	}

	@Override
	public List<Product> getProducts() {
		
		return productRepo.findAll();
	}

	@Override
	public boolean deleteProduct(String id) {
		
		Product product = findProductById(id);
		
		if(product != null){
			productRepo.deleteById(id);
			return true;
		}
		
		return false;
	}

	@Override
	public Product findProductById(String id) {
		
		return productRepo.findById(id)
	            .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
	}

	@Override
	public Product updateProduct(Product product) {
		
		return productRepo.findById(product.getId()).orElse(null) != null ? productRepo.save(product) : null;
	}

	@Override
	public List<Product> getAllProduct() {
		
		return productRepo.findAll();
	}

	@Override
	public List<Product> getTrendingProduct() {
		
		return productRepo.findByIsTrendingTrue();
	}

	@Override
	public List<Product> getLatestProduct() {
		
		return productRepo.findByIsLatestProductTrue();
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		
		return productRepo.findByCategory(category);
	}

	@Override
	public String getImageData(String productId) {
		Optional<ImageDataDTO> result = productRepo.findImageDataById(productId);
        return result.map(ImageDataDTO::getImageData).orElse(null);
	}

}
