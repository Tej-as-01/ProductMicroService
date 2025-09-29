package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping()
	public List<Product> getProducts()
	{
		return productService.getAllProducts(); 
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") Long id)
	{
		return productService.getProductById(id);
	}
	
	@PostMapping()
	public Product createProduct(@RequestBody Product product)
	{
		return productService.createProduct(product);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product)
	{
		return productService.updateProduct(id, product);
		
	}
	
	@DeleteMapping()
	public ResponseEntity<String> deleteAllProducts()
	{
		productService.deleteAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body("All products are deleted");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id)
	{
		productService.deleteProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product with ID "+id+" is deleted");
	}
	
	@PutMapping("/{id}/{quantity}")
	public ResponseEntity<String> reserveProduct(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity) {
	    boolean success = productService.reserveProduct(productId, quantity);
	    if (success) {
	        return ResponseEntity.ok("Product reserved");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient quantity");
	    }
	}
	
	@PutMapping("/restore/{id}/{quantity}")
	public ResponseEntity<String> restoreProduct(@PathVariable("id") Long id,@PathVariable("quantity") int quantity)
	{
		productService.restoreProduct(id,quantity);
		return ResponseEntity.ok("Product quantity restored");
	}
}
