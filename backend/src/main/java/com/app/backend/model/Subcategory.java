package com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@Entity
@Table(name ="subcategories")

public class Subcategory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 100)
    private String name;

    
    @Column(length = 500)
    private String description;

    @Column(nullable = false )
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name ="category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription (String description){
        this.description = description;
    }
     
    public String getDescription(){
        return description;
    }

    public void setActive (Boolean active){
        this.active = active;
    }

    public Boolean getActive(){
        return active;
    }
    public void setProducts(List<Product> products){
        this.products = products;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory (Category category){
        this.category = category;
    }
}
