package com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@Entity
@Table(name ="categories")

public class Category {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;

    
    @Column(length = 500)
    private String description;

    @Column(nullable = false )
    private Boolean active = true;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<Subcategory> subcategories;


     public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
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

    public List<Subcategory> getSubcategories(){
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories){
        this.subcategories = subcategories;
    }   
}
