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

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * RestController for handling product-related operations.
 * Provides endpoints for creating, retrieving, updating, deleting,
 * reserving, and restoring products.
 */
@RestController
@RequestMapping("/products")
@Tag(name="Product Controller", description="Handles CRUD operations for Products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
     * Retrieves all products.
     * @return list of all products
     */
	@Operation(summary="Get all Products",description="Returns the list of all products present")
	@GetMapping()
	public List<Product> getProducts()
	{
		return productService.getAllProducts(); 
	}
	
	/**
     * Retrieves a product by its ID.
     * @param id the product ID
     * @return the product entity
     */
	@Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") Long id)
	{
		return productService.getProductById(id);
	}
	
    /**
     * Creates a new product.
     * @param product the product to create
     * @return the saved product entity
     */
	@Operation(summary = "Create a new product", description = "Creates a new product and returns the saved entity")
	@PostMapping()
	public Product createProduct(@RequestBody Product product)
	{
		return productService.createProduct(product);
	}
	
	  /**
     * Updates an existing product by its ID.
     * @param id the product ID
     * @param product the updated product data
     * @return the updated product entity
     */
	@Operation(summary = "Update the product by ID", description = "Updates the existing product by its ID")
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product)
	{
		return productService.updateProduct(id, product);
		
	}
	
    /**
     * Deletes all products from the database.
     * @return confirmation message
     */
	@Operation(summary = "Delete all products", description = "Deletes all products from the database")
	@DeleteMapping()
	public ResponseEntity<String> deleteAllProducts()
	{
		productService.deleteAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body("All products are deleted");
	}
	

    /**
     * Deletes a product by its ID.
     * @param id the product ID
     * @return confirmation message
     */
	@Operation(summary = "Delete product by ID", description = "Deletes a specific product by its ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id)
	{
		productService.deleteProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product with ID "+id+" is deleted");
	}
	

    /**
     * Reserves a specified quantity of a product by its ID.
     * @param productId the product ID
     * @param quantity the quantity to reserve
     * @return success or failure message
     */
	@Operation(summary = "Reserves the product by ID", description = "Reserves the specified quantity of the product by its ID if available")
	@PutMapping("/{id}/{quantity}")
	public ResponseEntity<String> reserveProduct(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity) {
	    boolean success = productService.reserveProduct(productId, quantity);
	    if (success) {
	        return ResponseEntity.ok("Product reserved");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient quantity");
	    }
	}
	 /**
     * Restores a specified quantity to a product by its ID.
     * @param id the product ID
     * @param quantity the quantity to restore
     * @return confirmation message
     */
	@Operation(summary = "Restores the product by ID", description = "Restores the specified quantity to the product by its ID")
	@PutMapping("/restore/{id}/{quantity}")
	public ResponseEntity<String> restoreProduct(@PathVariable("id") Long id,@PathVariable("quantity") int quantity)
	{
		productService.restoreProduct(id,quantity);
		return ResponseEntity.ok("Product quantity restored");
	}

    /**
     * Retrieves products by category.
     * @param category the product category
     * @return list of products in the specified category
     */
	@Operation(summary = "Get products by category", description = "Returns products that match the given category")
	@GetMapping("/category/{category}")
	public List<Product> getProductsByCategory(@PathVariable("category") String category) {
	    return productService.getProductsByCategory(category);
	}
	
    /**
     * Retrieves products by category and price range
     * @param category the product category
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of matching products
     */

	@Operation(summary = "Get products by price range and category", description = "Returns products within a price range and category")
	@GetMapping("/filter/{category}/{minPrice}/{maxPrice}")
	public List<Product> getProductsByPriceRangeAndCategory(@PathVariable("category") String category, @PathVariable("minPrice") int minPrice,@PathVariable("maxPrice") int maxPrice) {
	    return productService.getProductsByPriceRangeAndCategory(minPrice, maxPrice, category);
	}

}
