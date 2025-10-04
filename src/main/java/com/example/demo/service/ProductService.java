package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

/**
 * Service class for managing product-related operations.
 * Provides methods for CRUD operations, stock management, and filtering.
 */
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	 /**
     * Retrieves all products from the database.
     * @return list of all products
     */
	public List<Product> getAllProducts()
	{
		return productRepository.findAll();
	}
	 /**
     * Retrieves a product by its ID.
     * @param id the product ID
     * @return the product entity
     * @throws ResourceNotFoundException if the product is not found
     */
	public Product getProductById(Long id)
	{
		return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The product with ID "+id+" not found"));
	}
	
	 /**
     * Creates a new product.
     * @param product the product to be created
     * @return the saved product entity
     */
	public Product createProduct(Product product)
	{
		return  productRepository.save(product);
	}
	
   /**
     * Updates an existing product by its ID.
     * @param id the product ID
     * @param product the updated product data
     * @return the updated product entity
     * @throws ResourceNotFoundException if the product is not found
     */
	public Product updateProduct(Long id, Product product) {
	    Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The product with ID " + id + " not found"));
	   
	    existingProduct.setName(product.getName());
	    existingProduct.setQuantity(product.getQuantity());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setPrice(product.getPrice());

	    return productRepository.save(existingProduct);
	}
	
	/**
     * Deletes all products from the database.
     */
	public void deleteAllProducts()
	{
		productRepository.deleteAll();
	}
	
   /**
     * Deletes a product by its ID.
     * @param id the product ID
     * @throws ResourceNotFoundException if the product is not found
     */
	
	public void deleteProductById(Long id)
	{
		if(!productRepository.existsById(id))
		{
			throw new  ResourceNotFoundException("The product with ID " + id + " not found");
		}
		
		productRepository.deleteById(id);
	}
	
	 /**
     * Reserves a specified quantity of a product if available.
     *
     * @param id the product ID
     * @param quantity the quantity to reserve
     * @return true if reservation is successful, false otherwise
     * @throws ResourceNotFoundException if the product is not found
     */
	public boolean reserveProduct(Long id, int quantity) {
	    Product product = productRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

	    if (product.getQuantity() >= quantity) {
	        product.setQuantity(product.getQuantity() - quantity);
	        productRepository.save(product);
	        return true;
	    }

	    return false;
	}
	
	/**
     * Restores a specified quantity to a product's stock.
     *
     * @param id the product ID
     * @param quantity the quantity to restore
     * @throws ResourceNotFoundException if the product is not found
     */
	public void restoreProduct(Long id,int quantity)
	{
		Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with ID "+id+" not found "));
		product.setQuantity(product.getQuantity()+quantity);
		productRepository.save(product);
	}
	
	/**
     * Retrieves products by category.
     *
     * @param category the product category
     * @return list of products in the specified category
     */
	public List<Product> getProductsByCategory(String category) {
	    return productRepository.findByCategory(category);
	}
	
    /**
     * Retrieves products by category and price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param category the product category
     * @return list of products matching the criteria
     */
	public List<Product> getProductsByPriceRangeAndCategory(int minPrice, int maxPrice, String category) {
	    return productRepository.findByPriceBetweenAndCategory(minPrice, maxPrice, category);
	}

}
