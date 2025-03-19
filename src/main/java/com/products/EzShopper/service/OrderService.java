package com.products.EzShopper.service;

import java.util.List;

import com.products.EzShopper.model.Order;

public interface OrderService {
	
	public List<Order> getOrdersByUserId(String userId);
	public Order saveOrder(Order order);
	public boolean deleteOrderById(String id);
	public List<Order> getAllOrders();
	public Order getOrdersByOrderId(String orderId);
}
