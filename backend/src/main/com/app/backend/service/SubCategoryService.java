package com.app.backend.service;

import com.app.backend.model.SubCategory;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    public List<SubCategory> findByCategoryId(Long categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }

    public SubCategory findById(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(() -> new
        RuntimeException("SubCategoría no encontrada"));
    }

    public SubCategory create(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }
    public SubCategory update(Long id, SubCategory subcategoryDetails){
        SubCategory subcategory = findById(id);
        subcategory.setName(subcategoryDetails.getName());
        subcategory.setDescription(subcategoryDetails.getDescription());
        subcategory.setActive(subcategoryDetails.getActive());
        subcategory.setCategory(subcategoryDetails.getCategory());  
        return subCategoryRepository.save(subcategory);
    }
    public void delete(Long id) {
        SubCategory subCategory = findById(id);
        subCategoryRepository.delete(subCategory);
    }
}