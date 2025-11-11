package com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.annotation.Inherited;
import java.util.list;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.All)
    @JsonIgnore 
    private list<subcategory> subcategories;


     public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public Void setDescription (String description){
        this.description = description;
    }
     
    public String getDescription(){
        return description;
    }

    public Void setActive (Boolean active){
        this.active = active;
    }

    public Boolean getActive(){
        return active;
    }

    public List<subcategory> getSubcategories(){
        return subcategories;
    }

    public void setSubcategories(List<subcategory> subcategories){
        this.subcategories = subcategories;
    }   
}
