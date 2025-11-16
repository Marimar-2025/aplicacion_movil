package com.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.model.Subcategory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByCategoryId(Long categoryId);
}

  
