package com.angMetal.orders.service;

import com.angMetal.orders.entity.Product;
import com.angMetal.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create or Update a Product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product by ID
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Get Product by Name
    public List<Product> getProductById(String name) {
        return productRepository.findByName(name);
    }

    // Delete Product by ID
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
