package com.example.demo.Exceptions;

/**
 * ResourceNotFoundException is thrown when the requested
 * resource is not found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
