package com.uade.tpo.libreria.tpolibreria.service;
 
import java.util.ArrayList;
import java.util.Optional;
 
import com.uade.tpo.libreria.tpolibreria.entity.Category;
import com.uade.tpo.libreria.tpolibreria.exceptions.CategoryDuplicateException;
import com.uade.tpo.libreria.tpolibreria.repository.CategoryRepository;
 
public class CategoryService {
    private CategoryRepository categoryRepository;
 
    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }
 
    public ArrayList<Category> getCategories() {
        return categoryRepository.getCategories();
    }
 
    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }
 
    public Category createCategory(int newCategoryId, String description) throws CategoryDuplicateException {
        ArrayList<Category> categories = categoryRepository.getCategories();
        if (categories.stream().anyMatch(
                category -> category.getId() == newCategoryId && category.getDescription().equals(description)))
            throw new CategoryDuplicateException();
        return categoryRepository.createCategory(newCategoryId, description);
    }
}