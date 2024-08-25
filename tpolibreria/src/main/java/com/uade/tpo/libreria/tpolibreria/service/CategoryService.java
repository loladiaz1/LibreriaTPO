
package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

//import com.uade.tpo.libreria.tpolibreria.entity.Category;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCategoriaDuplicada;

public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Optional<Category> getCategoryById(Long categoryId);

    public Category createCategory(String description) throws ExcepcionCategoriaDuplicada;
}