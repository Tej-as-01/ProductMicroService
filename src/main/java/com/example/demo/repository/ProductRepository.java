package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
	
	@Query("SELECT p FROM Product p WHERE LOWER(p.category) LIKE LOWER(:category)")
	List<Product> findByCategory(@Param("category") String category);
	
	@Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <=:maxPrice AND p.category = :category")
	List<Product> findByPriceBetweenAndCategory( @Param("minPrice") int minPrice,@Param("maxPrice") int maxPrice,@Param("category") String category);



}
