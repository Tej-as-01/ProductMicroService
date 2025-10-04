package com.example.demo.Exceptions;

/**
 * ProductStockException is thrown when there isn't enough stock available
 * to fulfill a customer's order.
 */
public class ProductStockException extends RuntimeException {
	public ProductStockException(String message)
	{
		super(message);
	}

}
