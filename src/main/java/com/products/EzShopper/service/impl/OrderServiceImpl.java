package com.products.EzShopper.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.EzShopper.model.Order;
import com.products.EzShopper.repo.OrderRepo;
import com.products.EzShopper.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepo orderRepo;

	@Override
	public List<Order> getOrdersByUserId(String userId) {
		return orderRepo.findOrdersByUserId(userId);
	}

	@Override
	public Order saveOrder(Order order) {
		
		return orderRepo.save(order);
	}

	@Override
	public boolean deleteOrderById(String id) {
			
		try {
			orderRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
