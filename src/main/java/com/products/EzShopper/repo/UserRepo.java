package com.products.EzShopper.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.products.EzShopper.model.CustomUser;

@Repository
public interface UserRepo extends MongoRepository<CustomUser, String> {

	Optional<CustomUser> findByEmail(String email);

	boolean existsByEmail(String email);

	List<CustomUser> findByRole(String role);

	Optional<CustomUser> findByResetPasswordToken(String token);
	
//	@Query(value = "{'email': ?0}", fields = "{'cart': 1, '_id': 0}")
//	Cart findCartByEmail(String email);

}
