package com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@Entity
@Table(name ="products")

public class Product {
     @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    
    @Column(length = 1000)
    private String description;

    @Column(nullable = false )
    private Double price;

    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name ="category_id", nullable = false)
    private Category category;
    
    @ManyToOne
    @JoinColumn(name ="subcategory_id", nullable = false)
    private Subcategory subcategory;


    public void setName(String name){
        this.name = name;
    }

     public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
     
   public double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Integer getStock(){
        return stock;
    }
    public void setStock(Integer stock){
        this.stock = stock;
    }
     public Boolean getActive(){
        return active;
    }

    public void setActive (Boolean active){
        this.active = active;
    }

   public void setCategory (Category category){
        this.category = category;
    }

    public Category getCategory(){
        return category;
    }
    
    public void setSubcategory (Subcategory subcategory){
        this.subcategory = subcategory;
    }

      public Subcategory getSubcategory(){
        return subcategory;
    }

}
