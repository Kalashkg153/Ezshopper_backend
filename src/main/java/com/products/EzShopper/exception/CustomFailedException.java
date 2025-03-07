package com.products.EzShopper.exception;

import org.springframework.http.HttpStatus;

public class CustomFailedException extends RuntimeException {
	
	private final HttpStatus status;
	
	private static final long serialVersionUID = 1L;

	public CustomFailedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
	
	public HttpStatus getStatus() {
        return status;
    }
}
