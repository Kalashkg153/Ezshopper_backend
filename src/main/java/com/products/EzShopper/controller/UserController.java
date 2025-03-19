package com.products.EzShopper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.EzShopper.config.JwtUtil;
import com.products.EzShopper.exception.CustomFailedException;
import com.products.EzShopper.model.ChangePasswordRequest;
import com.products.EzShopper.model.CustomUser;
import com.products.EzShopper.model.LoginRequest;
import com.products.EzShopper.model.LoginResponse;
import com.products.EzShopper.model.SuccessResponse;
import com.products.EzShopper.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public SuccessResponse registerUser(@RequestBody CustomUser user) {
		
		try {
			if(!userService.registerUser(user)) {
				throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Create your Account");
			}
			else {
				return new SuccessResponse("SUCCESS", "Your account has been created successfully.");
			}
		} catch (Exception e) {
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public LoginResponse loginUser(@RequestBody LoginRequest loginReq){
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtil.generateToken(userDetails.getUsername());
			
			System.out.println(jwtUtil.extractExpiration(token));
			
			if(token != null && token != "") {
				return new LoginResponse(userDetails.getUsername(), userDetails.getAuthorities().toString(), token, "Logged in Successfully");
			}
			
			
			return new LoginResponse(userDetails.getUsername(), userDetails.getAuthorities().toString(), "", "Failed to Login. Please try again");
			
		} catch(BadCredentialsException e) {
			throw new CustomFailedException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password");
		}
	}
	
	@PostMapping("/update-profile/{userId}")
	public SuccessResponse updateUserProfile(@RequestBody CustomUser user, @PathVariable String userId) {
		
		CustomUser foundUser = userService.findById(userId);
		
			
			foundUser.setEmail(user.getEmail());
			foundUser.setAddress(user.getAddress());
			foundUser.setContact(user.getContact());
			foundUser.setFullName(user.getFullName());
			foundUser.setPincode(user.getPincode());
			
			CustomUser updatedUser = userService.saveUser(foundUser);
			if(updatedUser != null) {
				return new SuccessResponse("SUCCESS", "Profile Updated");
			}
				
			
			throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot Update Profile at the momemt, Please try again Later");
	}
	
	@PostMapping("/update-password/{userId}")
	public SuccessResponse updateUserPassword(@RequestBody ChangePasswordRequest passwordReq, @PathVariable String userId) {
		
		CustomUser foundUser = userService.findById(userId);
		
			
		if(passwordEncoder.matches(passwordReq.getOldPassword(), foundUser.getPassword())) {
			if(userService.updatePassword(passwordReq, foundUser)) {
				return new SuccessResponse("SUCCESS","Password Changed!");
			}
			else {
				throw new CustomFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot Update Password at the moment, Please try again later");
			}
		}
		else {
			throw new CustomFailedException(HttpStatus.UNAUTHORIZED, "Wrong Old Password!");
		}
			
	}
	
	
}
