package com.products.EzShopper.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.products.EzShopper.model.CustomUser;
import com.products.EzShopper.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<CustomUser> user = userRepo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with Email : " + email);
		}
		
		UserDetails userDetails = User.withUsername(user.get().getEmail())
				.password(user.get().getPassword())
				.roles(user.get().getRole())
				.build();
		
		return userDetails;
		
	}

}
