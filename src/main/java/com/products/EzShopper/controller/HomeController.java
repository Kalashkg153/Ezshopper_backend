package com.products.EzShopper.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.products.EzShopper.model.ResetPasswordRequest;
import com.products.EzShopper.model.SuccessResponse;
import com.products.EzShopper.service.CategoryService;
import com.products.EzShopper.service.OrderService;
import com.products.EzShopper.service.ProductService;
import com.products.EzShopper.service.UserService;
import com.products.EzShopper.util.CommonUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@GetMapping("/activeCategories")
	public List<Category> getActiveCategories(){
		
		return categoryService.getActiveCategories();
	}
	
	@GetMapping("/getAllProducts")
	public List<Product> getAllProducts(){
			
		return productService.getProducts();
	}
	
	@PostMapping("/forgetPasswordLink")
	public SuccessResponse userForgetPassword(@RequestParam String email , HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
		
		if (!userService.userExistByEmail(email)) {
			throw new CustomFailedException(HttpStatus.NOT_FOUND , "Account Does not Exist with Email : " + email);
	    }

	    String resetToken = UUID.randomUUID().toString();
	    CustomUser user = userService.findUserByEmail(email);
	    user.setResetPasswordToken(resetToken);
	    userService.saveUser(user);

	    String resetLink = commonUtil.generateLink(request) + "/reset-password?resetToken=" + resetToken;

	    boolean isMailSent = commonUtil.sendEmailToUser(email, resetLink);
	    
	    if (!isMailSent) {
	        throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something went wrong while sending the password reset link. Please try again later.");
	    }

	    return new SuccessResponse("SUCCESS" , "A password reset link has been sent to your email. Please check your inbox.");
		
	}
	
	@PostMapping("/reset-password")
	public SuccessResponse resetPassword(@RequestBody ResetPasswordRequest req){
		
		CustomUser user = userService.findByResetPasswordToken(req.getToken());
		
		try {
			
			user.setPassword(passwordEncoder.encode(req.getNewPassword()));
		    user.setResetPasswordToken(null);
		    userService.saveUser(user);

		    return new SuccessResponse("SUCCESS" , "Password Updated Successfully");
		    
		} catch(Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in updating password. Please try again later.");
		}
		
	}
	
	@GetMapping("/getProducts")
	public List<Product> getAllProduct(){
		
		List<Product> products = null;
		
		try {
			
			products = productService.getAllProduct();
			
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Fetching Products. Please try again later.");
		}
		
		if(products == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Products Found");
		}
		
		return products;
	}
	
	@GetMapping("/trendingProducts")
	public List<Product> getTrendingProduct(){
		
		List<Product> products = null;
		
		try {
			
			products = productService.getTrendingProduct();
			
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Fetching Products. Please try again later.");
		}
		
		if(products == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Products Found");
		}
		
		
		return products;
	}
	
	@GetMapping("/latestProducts")
	public List<Product> getLatestProduct(){
		
		List<Product> products = null;
		
		try {
			products = productService.getLatestProduct();
			
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Fetching Products. Please try again later.");
		}
		
		if(products == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Products Found");
		}
		
		
		return products;
	}
	
	@GetMapping("/getProductsByCategory/{category}")
	public List<Product> getProductByCategory(@PathVariable String category){
		
		List<Product> products = null;
		
		try {
			products = productService.getProductByCategory(category);
			
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Fetching Products. Please try again later.");
		}
		
		if(products == null) {
			throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Products Found");
		}
		
		return products;
	}
	
	@GetMapping("/getProductById/{productId}")
	public Product getProductById(@PathVariable String productId){
		
		return productService.findProductById(productId);
		
	}
	
	
	@GetMapping("/user/{userName}")
	public CustomUser getUserByEmail(@PathVariable String userName){
		
		return userService.findUserByEmail(userName);
	}
	
	 @GetMapping("/productImageData/{productId}")
    public String getImage(@PathVariable String productId) {
		 
		 String productImage = null;
		 
		 try {
			 productImage = productService.getImageData(productId);
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Fetching Product Image with Product Id : " + productId);
		}
		 
		if(productImage == null) {
			throw new CustomFailedException(HttpStatus.NOT_FOUND, "Product Image not Found for Product Id : " + productId);
		}
		 
        return productImage;
    }
	 
	 
	 @PostMapping("/placeOrder")
	 public SuccessResponse saveOrder(@RequestBody Order order){
		 
		 CustomUser user = userService.findUserByEmail(order.getUserId());  // Now throws an exception if user is missing
		 
		 order.setOrderStatus("ORDER_RECEIVED");
		  Order newOrder = orderService.saveOrder(order);
		
		    if (newOrder == null) {
		    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Place the Order");
		    }
		
		    user.setCart(null);
		    userService.saveUser(user);
		
		 return new SuccessResponse("SUCCESS", "Order Placed Successfully");
	 }
	 
	 
	 @GetMapping("user/getOrders/{username}")
	 public List<Order> getOrders(@PathVariable String username){
		 
		List<Order> foundOrders = orderService.getOrdersByUserId(username);

	    if (foundOrders.isEmpty()) {
	    	throw new CustomFailedException(HttpStatus.NO_CONTENT, "No Orders found");
	    }

	    return foundOrders; 
	 }
	 
	 @DeleteMapping("order/{id}")
	    public SuccessResponse deleteOrder(@PathVariable String id) {
		 boolean deleted = orderService.deleteOrderById(id);

		    if (deleted) {
		        return new SuccessResponse("SUCCESS", "You Cancelled your order with Order Id: " + id);
		    }

		    throw new CustomFailedException(HttpStatus.NOT_FOUND, "Order not found with Id : " + id);
	    }
	 
	 
	 
	
	
	
	
}
