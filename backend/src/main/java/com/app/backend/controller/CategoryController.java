package com.app.backend.controller;

import com.app.backend.model.Category;            
import com.app.backend.service.CategoryService;    
import com.app.backend.dto.MessagerResponse;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessagerResponse> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new MessagerResponse("Categoría eliminada exitosamente"));
    }
}