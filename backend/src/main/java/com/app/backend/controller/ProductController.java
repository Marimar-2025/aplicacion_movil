package com.app.backend.controller;

import com.app.backend.model.Product;
import com.app.backend.service.ProductService;
import com.app.backend.dto.MessagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;  

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @GetMapping("/subcategory/{subcategoryId}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getProductsBySubcategoryId(@PathVariable Long subcategoryId) {
        return ResponseEntity.ok(productService.findBySubcategoryId(subcategoryId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.create(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessagerResponse> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new MessagerResponse("Producto eliminado exitosamente"));
    }   
}
