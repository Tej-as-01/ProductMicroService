package com.example.demo.repository;

import com.example.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("mysql-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Integration-Test Save Product")
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setCategory("Electronics");
        product.setQuantity(10);
        product.setPrice(75000);

        Product saved = productRepository.save(product);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Integration-Test Find Product by ID")
    public void testFindById() {
        Product product = new Product();
        product.setName("Phone");
        product.setCategory("Electronics");
        product.setQuantity(5);
        product.setPrice(30000);

        Product saved = entityManager.persistAndFlush(product);
        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Phone");
    }

    @Test
    @DisplayName("Integration-Test Find All Products")
    public void testFindAll() {
        Product product1 = new Product();
        product1.setName("Tablet");
        product1.setCategory("Electronics");
        product1.setQuantity(7);
        product1.setPrice(25000);

        Product product2 = new Product();
        product2.setName("Chair");
        product2.setCategory("Furniture");
        product2.setQuantity(15);
        product2.setPrice(5000);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("Integration-Test Delete Product by ID")
    public void testDeleteById() {
        Product product = new Product();
        product.setName("Desk");
        product.setCategory("Furniture");
        product.setQuantity(3);
        product.setPrice(12000);

        Product saved = entityManager.persistAndFlush(product);
        productRepository.deleteById(saved.getId());

        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Integration-Test Delete All Products")
    public void testDeleteAll() {
        Product product1 = new Product();
        product1.setName("Mouse");
        product1.setCategory("Electronics");
        product1.setQuantity(20);
        product1.setPrice(1500);

        Product product2 = new Product();
        product2.setName("Keyboard");
        product2.setCategory("Electronics");
        product2.setQuantity(10);
        product2.setPrice(2500);

        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        productRepository.deleteAll();
        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).isEmpty();
    }
}
