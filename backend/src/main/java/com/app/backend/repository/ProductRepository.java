package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySubcategoryId(Long subcategoryId);
}
