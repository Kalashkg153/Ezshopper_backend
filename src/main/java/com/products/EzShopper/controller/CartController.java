package com.products.EzShopper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.EzShopper.exception.CustomFailedException;
import com.products.EzShopper.model.CartRequest;
import com.products.EzShopper.model.SuccessResponse;
import com.products.EzShopper.service.UserService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/addToCart")
	public SuccessResponse addItemToCart(@RequestBody CartRequest cartReq) {
		
		try {
	        boolean isAdded = userService.addItemToCart(cartReq);

	        if (isAdded) {
	            return new SuccessResponse("PRODUCT_ADD","Item added to Your cart.");
	        } else {
	        	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Add Item to Your Cart");
	        }

	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while adding the item to the cart.");
	    }
		
	}
	
	
	@PostMapping("/decreaseQuantityFromCart")
	public boolean decreaseQuantityFromCart(@RequestBody CartRequest cartReq) {
		
		try {
	        boolean isDecreased = userService.decreaseQuantityFromCart(cartReq);

	        if (!isDecreased) {
	        	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to decrease item quantity. Please check the request.");
	        }

	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while decreasing item quantity.");
	    
	    }
		
		return true;
		
	}
	
	@PostMapping("/removeItemFromCart")
	public boolean removeItemFromCart(@RequestBody CartRequest cartReq) {
		
		try {
	        boolean isRemoved = userService.removeItemFromCart(cartReq);

	        if (!isRemoved) {
	        	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to remove item from cart. Item not found or request is invalid.");
	            
	        }

	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while removing the item from the cart.");
	       
	    }
		
		return true;
	}
	
	
	
	@PostMapping("/emptyCart")
	public void emptyTheCart(@RequestBody CartRequest cartReq) {
		
		try {
	        boolean isEmptied = userService.emptyCart(cartReq);

	        if (isEmptied)  {
	        	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to empty cart. Cart is already empty or request is invalid.");
	            
	        }

	    } catch (Exception e) {
	    	throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while trying to empty the cart.");
	 
	    }
		
	}
	
	
	
//	@GetMapping("/getCart/{userName}")
//	public ResponseEntity<?> getUserCart(@PathVariable String userName){
//		
//		if(userService.userExistByEmail(userName)) {
//			Cart cart = userService.getUsersCart(userName);
//			
//			if(cart != null) {
//				return new ResponseEntity<>(cart, HttpStatus.OK);
//			}
//			else {
//				return new ResponseEntity<>(new Cart(), HttpStatus.OK);
//			}
//		}
//		
//		
//		return new ResponseEntity<>("Username does not exist", HttpStatus.OK);
//	}
}
