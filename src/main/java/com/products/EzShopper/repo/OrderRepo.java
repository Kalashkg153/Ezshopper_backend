package com.products.EzShopper.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.products.EzShopper.model.Order;

public interface OrderRepo extends MongoRepository<Order, String> {
	
	List<Order> findOrdersByUserId(String userId);
}
