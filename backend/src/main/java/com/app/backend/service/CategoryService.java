package com.app.backend.service;    

import com.app.backend.model.Category;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new
        RuntimeException("Categoría no encontrada"));
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }
    

    public Category update(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setActive(categoryDetails.getActive());
        return categoryRepository.save(category);
    }
    
    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}



//"subcategorias y producto"//
