package com.products.EzShopper.service;

import java.util.List;
import java.util.Optional;

import com.products.EzShopper.model.Product;

public interface ProductService {
	
	public Product saveProduct(Product product);
	public Product findProductByTitle(String title);
	public List<Product> getProducts();
	public boolean deleteProduct(String id);
	public Product findProductById(String id);
	public Product updateProduct(Product product);
	public List<Product> getAllProduct();
	public List<Product> getTrendingProduct();
	public List<Product> getLatestProduct();
	public List<Product> getProductByCategory(String category);
	public String getImageData(String productId);
	
	

}

