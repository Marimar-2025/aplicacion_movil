package com.app.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Data
@Entity
@Table(name = "subcategories")
public class Subcategory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
