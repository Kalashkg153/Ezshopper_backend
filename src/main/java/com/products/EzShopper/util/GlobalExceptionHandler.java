package com.products.EzShopper.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.products.EzShopper.exception.CustomFailedException;
import com.products.EzShopper.exception.ProductNotFoundException;
import com.products.EzShopper.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomFailedException.class)
	public ResponseEntity<?> handleCustomFailedException(CustomFailedException ex){
		HttpStatus status = ex.getStatus();
		
		System.out.println("called this");
		
		return ResponseEntity.status(status).body(ex.getMessage());
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateValuesException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
	
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
	
	
	@ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
	
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<String> handleAuthException(AuthorizationDeniedException ex){
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have Access to these Services");
	}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong: " + ex.getMessage());
    }
}
