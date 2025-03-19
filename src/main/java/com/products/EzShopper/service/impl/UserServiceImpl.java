package com.products.EzShopper.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.products.EzShopper.exception.UserNotFoundException;
import com.products.EzShopper.model.Cart;
import com.products.EzShopper.model.CartItem;
import com.products.EzShopper.model.CartRequest;
import com.products.EzShopper.model.ChangePasswordRequest;
import com.products.EzShopper.model.CustomUser;
import com.products.EzShopper.repo.UserRepo;
import com.products.EzShopper.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public CustomUser saveUser(CustomUser user) {
		
		return userRepo.save(user);
	}

	@Override
	public CustomUser findUserByEmail(String email) {
		
		return userRepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not Registered"));
	}

	@Override
	public boolean registerUser(CustomUser user) {
		
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new DataIntegrityViolationException("Account Already Exists with email : " + user.getEmail());
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(user.getRole() == null || user.getRole() == "") {
			user.setRole("USER");
		}
		user.setCart(new Cart());

		CustomUser newuser = saveUser(user);
		
		if(newuser == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<CustomUser> findAll() {
		
		return userRepo.findAll();
	}

	@Override
	public List<CustomUser> findUserByRole(String role) {
		
		return userRepo.findByRole(role);
	}

	@Override
	public boolean existsById(String id) {
		
		return userRepo.existsById(id);
	}
	
	@Override
	public CustomUser findById(String id) {
		
		return userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with Id " + id + " not Registered"));
	}

	@Override
	public boolean userExistByEmail(String email) {
		
		return userRepo.existsByEmail(email);
	}

	@Override
	public CustomUser findByResetPasswordToken(String token) {
		
		return userRepo.findByResetPasswordToken(token)
				.orElseThrow(() -> new UserNotFoundException("Invalid URL or session has expired"));
	}

	@Override
	public boolean addItemToCart(CartRequest cartReq) {
		
		CustomUser user = userRepo.findByEmail(cartReq.getUserName())
				 .orElseThrow(() -> new UserNotFoundException("User with email " + cartReq.getUserName() + " not Registered"));
		
		if(user != null) {
			
			if(user.getCart() == null) {
				user.setCart(new Cart());
			}
			
			
			CartItem item = new CartItem(cartReq.getProductId(), cartReq.getProductImage(), cartReq.getProductName(),cartReq.getQuantity(), cartReq.getPrice());
			double totalPrice = user.getCart().getTotalPrice();
			List<CartItem> items = user.getCart().getItems();
			if(items == null) {
				items = new ArrayList<CartItem>();
			}
//			items.add(item);
			boolean exists = items.stream().anyMatch(cartItem -> cartItem.getProductId().equals(cartReq.getProductId()));
			if(exists) {
				for(CartItem cartItem : items) {
					if(cartItem.getProductId().equals(item.getProductId())) {
						cartItem.setQuantity(cartItem.getQuantity() + 1);
						totalPrice += cartItem.getPrice();
					}
				}
			}
			else {
				items.add(item);
				totalPrice += item.getPrice();
			}
			user.getCart().setItems(items);
			user.getCart().setTotalPrice(totalPrice);
			
			saveUser(user);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public boolean emptyCart(CartRequest cartReq){
		
		CustomUser user = userRepo.findByEmail(cartReq.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User with email " + cartReq.getUserName() + " not Registered"));
		
		if(user != null) {
			
			user.setCart(new Cart());
			
			saveUser(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean decreaseQuantityFromCart(CartRequest cartReq) {
		
		CustomUser user = userRepo.findByEmail(cartReq.getUserName())
				.orElseThrow(() -> new UserNotFoundException("User with email " + cartReq.getUserName() + " not Registered"));
		
		if(user != null) {
				
			if(user.getCart() != null) {
				List<CartItem> items = user.getCart().getItems();
				
				double totalPrice = user.getCart().getTotalPrice();
				
				Iterator<CartItem> iterator = items.iterator();
			    while (iterator.hasNext()) {
			        CartItem item = iterator.next();
			        if (item.getProductId().equals(cartReq.getProductId())) {
			            if (item.getQuantity() > 1) {
			                item.setQuantity(item.getQuantity() - 1); 
			                totalPrice -= item.getPrice(); 
			            } else {
			                totalPrice -= item.getPrice(); 
			                iterator.remove();
			            }
			            break;
			        }
			    }
			    
			    user.getCart().setTotalPrice(totalPrice);
			    user.getCart().setItems(items);
			    saveUser(user);
			    return true;
			}
			
			return false;
		}
		
		return false;
		
	}

	@Override
	public boolean removeItemFromCart(CartRequest cartReq) {
		
		CustomUser user = userRepo.findByEmail(cartReq.getUserName())
					.orElseThrow(() -> new UserNotFoundException("User with email " + cartReq.getUserName() + " not Registered"));
		
		if(user != null) {
			if(user.getCart() != null) {
				List<CartItem> items = user.getCart().getItems();
				
				double totalPrice = user.getCart().getTotalPrice();
				
				Iterator<CartItem> iterator = items.iterator();
			    while (iterator.hasNext()) {
			        CartItem item = iterator.next();
			        if (item.getProductId().equals(cartReq.getProductId())) {
			                totalPrice -= (item.getPrice()*item.getQuantity()); 
			                iterator.remove();
			            break;
			        }
			    }
			    
			    user.getCart().setTotalPrice(totalPrice);
			    user.getCart().setItems(items);
			    saveUser(user);
			    return true;
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	public boolean updatePassword(ChangePasswordRequest req, CustomUser foundUser) {
			
		foundUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
		
		CustomUser newuser = saveUser(foundUser);
		
		if(newuser == null) {
			return false;
		}
		
		return true;
		
	}

	
	
}
