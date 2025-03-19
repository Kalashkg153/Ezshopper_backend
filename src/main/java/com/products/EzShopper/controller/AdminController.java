package com.products.EzShopper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.products.EzShopper.exception.CustomFailedException;
import com.products.EzShopper.model.Category;
import com.products.EzShopper.model.CustomUser;
import com.products.EzShopper.model.Order;
import com.products.EzShopper.model.Product;
import com.products.EzShopper.model.SuccessResponse;
import com.products.EzShopper.service.CategoryService;
import com.products.EzShopper.service.OrderService;
import com.products.EzShopper.service.ProductService;
import com.products.EzShopper.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/saveCategory")
	public SuccessResponse saveCategory(@RequestBody Category category) {
			
		if (categoryService.findCategoryByName(category.getName()) != null) {
			throw new CustomFailedException(HttpStatus.CONFLICT, "This Category Already Exists");
	        
	    }

	    try {
	        categoryService.saveCategory(category);
	        return new SuccessResponse("SUCCESS", "Category Added Successfully");
	        
	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Category Insertion Failed due to some Error");
	    }
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/categories")
	public List<Category> getCategories(){
		
		List<Category> categories = null;
		
		try {
			categories = categoryService.getCategories();
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "An Error Occured While Fetching Categories");
		}
		
		if(categories == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Categories Found");
		}
			
		return categories;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/deleteCategory/{id}")
	public SuccessResponse deleteCategory(@PathVariable String id) {
			
		boolean isDeleted = categoryService.deleteCategory(id);
		
		if(isDeleted) {
			return new SuccessResponse("SUCCESS", "Category Removed");
		}
		
		throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Category Removal Failed");
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/updateCategory")
	public SuccessResponse updateCategory(@RequestBody Category category){
		
		Category updatedCategory = categoryService.updateCategory(category);
		
		if(updatedCategory != null) {
			return new SuccessResponse("SUCCESS", "Category Updated");
			
		}
		
		throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Category Does Not Exists");
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/saveProduct")
	public SuccessResponse saveProduct(@RequestBody Product product){
			
		if (productService.findProductByTitle(product.getTitle()) != null) {
			throw new CustomFailedException(HttpStatus.CONFLICT, "This Product Already Exists");
	    }

	    try {
	        product.setDiscount(0);
	        product.setDiscountedPrice(product.getPrice());

	        productService.saveProduct(product);
	        return new SuccessResponse("SUCCESS", "Product Saved Successfully");

	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Product Insertion Failed due to some Error");
	    }
	}
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
			
		return productService.getProducts();
	}
	
	@GetMapping("/deleteProduct/{id}")
	public SuccessResponse deleteProduct(@PathVariable String id){
		
		boolean isDeleted = productService.deleteProduct(id);
		
		if(isDeleted) {
			return new SuccessResponse("SUCCESS", "Product Removed");
		}
		
		throw new CustomFailedException(HttpStatus.NOT_FOUND, "No Product Found with given Id");
	}
	
	
	@PostMapping("/updateProduct")
	public SuccessResponse updateProduct(@RequestBody Product product){
		
		productService.findProductById(product.getId()); 

	    if (product.getDiscount() > 0 && product.getDiscount() <= 100) {
	        double totalDiscount = (product.getDiscount() * product.getPrice()) / 100;
	        product.setDiscountedPrice(product.getPrice() - totalDiscount);
	    }

	    try {
	    	productService.updateProduct(product);
	    	return new SuccessResponse("SUCCESS", "Product Removed");
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in updating Product");
		}
	   
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public List<CustomUser> getAllUsers(){
		
		List<CustomUser> allUsers = userService.findUserByRole("USER");
		
		if(allUsers == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Users Found");
		}
		
		return allUsers;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/updateUserAccount")
	public SuccessResponse updateUserAccount(@RequestBody CustomUser user){
		
		if (user.getId() == null) {
			throw new CustomFailedException(HttpStatus.BAD_REQUEST, "User ID is required for updating the account.");
	    }

	    if (!userService.existsById(user.getId())) {
	    	throw new CustomFailedException(HttpStatus.NOT_FOUND, "Account does not exist with this ID.");
	        
	    }

	    try {
	        userService.saveUser(user);
	        return new SuccessResponse("SUCCESS","Account Updated Successfully");
	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating account.");
	        
	    }
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/getAllOrders")
	 public List<Order> getOrders(){
		 
		List<Order> foundOrders = orderService.getAllOrders();

	    if (foundOrders.isEmpty()) {
	    	throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Orders found");
	    }

	    return foundOrders; 
	 }
	
	@PostMapping("/updateOrderStatus/{orderId}")
	public boolean updateOrderStatus(@RequestParam String OrderStatus, @PathVariable String orderId) {
			
		Order order = orderService.getOrdersByOrderId(orderId);
		
		if(order == null) {
			throw new CustomFailedException(HttpStatus.NOT_FOUND, "Order not Found with id : " + orderId);
		}
		
		try {
			order.setOrderStatus(OrderStatus);
			orderService.saveOrder(order);
			return true;
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
	}
}
