package com.app.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;
}
