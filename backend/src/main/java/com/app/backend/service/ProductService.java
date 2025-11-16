package com.app.backend.service;

import com.app.backend.model.Product;
import com.app.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

     public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findBySubcategoryId(Long subcategoryId) {
        return productRepository.findBySubcategoryId(subcategoryId);
    }
    
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new
        RuntimeException("Producto no encontrado"));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product productDetails) {
        Product product = findById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setActive(productDetails.getActive());
        product.setCategory(productDetails.getCategory());
        product.setSubcategory(productDetails.getSubcategory());
        return productRepository.save(product);
    }
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}



