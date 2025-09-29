package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.repo.Product;
import com.example.demo.repo.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts()
	{
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id)
	{
		return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The product with ID "+id+" not found"));
	}
	
	public Product createProduct(Product product)
	{
		return  productRepository.save(product);
	}
	
	public Product updateProduct(Long id, Product product) {
	    Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The product with ID " + id + " not found"));

	   
	    existingProduct.setName(product.getName());
	    existingProduct.setQuantity(product.getQuantity());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setPrice(product.getPrice());

	    return productRepository.save(existingProduct);
	}
	
	public void deleteAllProducts()
	{
		productRepository.deleteAll();
	}
	
	public void deleteProductById(Long id)
	{
		if(!productRepository.existsById(id))
		{
			throw new  ResourceNotFoundException("The product with ID " + id + " not found");
		}
		
		productRepository.deleteById(id);
	}
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
	
	public void restoreProduct(Long id,int quantity)
	{
		Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with ID "+id+" not found "));
		product.setQuantity(product.getQuantity()+quantity);
		productRepository.save(product);
	}
}
