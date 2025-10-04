package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Product;

/**
 * Repository interface for managing {@link Product} entities.
 * Provides custom query methods for filtering products by category and price range.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
	
	 /**
     * Retrieves products that match the specified category.
     * @param category the category to filter by
     * @return list of products in the given category
     */
	@Query("SELECT p FROM Product p WHERE LOWER(p.category) LIKE LOWER(:category)")
	List<Product> findByCategory(@Param("category") String category);
	
	 /**
     * Retrieves products within a specified price range and category.
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @param category the category to filter by
     * @return list of products matching the criteria
     */
	@Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <=:maxPrice AND p.category = :category")
	List<Product> findByPriceBetweenAndCategory( @Param("minPrice") int minPrice,@Param("maxPrice") int maxPrice,@Param("category") String category);



}
