package com.products.EzShopper.service;

import java.util.List;
import java.util.Optional;

import com.products.EzShopper.model.CartRequest;
import com.products.EzShopper.model.ChangePasswordRequest;
import com.products.EzShopper.model.CustomUser;

public interface UserService {
	
	public CustomUser saveUser(CustomUser user);

	public CustomUser findUserByEmail(String email);
	
	public boolean userExistByEmail(String email);
	
	public boolean registerUser(CustomUser user);
	
	public List<CustomUser> findAll();

	public List<CustomUser> findUserByRole(String role);

	public boolean existsById(String id);
	
	public CustomUser findById(String id);
	
	public boolean updatePassword(ChangePasswordRequest req, CustomUser foundUser);
	
	public CustomUser findByResetPasswordToken(String token);
	
	public boolean addItemToCart(CartRequest cartReq);
	
	public boolean emptyCart(CartRequest cartReq);
	
	public boolean decreaseQuantityFromCart(CartRequest cartReq);
	
	public boolean removeItemFromCart(CartRequest cartReq);
	
//	public Cart getUsersCart(String userName);
}
