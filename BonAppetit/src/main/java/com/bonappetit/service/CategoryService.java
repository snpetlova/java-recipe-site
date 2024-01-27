package com.bonappetit.service;

import com.bonappetit.model.entity.CategoryEntity;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> getAllCategories() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        List<String> allCategoriesNames = new ArrayList<>();

        for (CategoryEntity currCategory : allCategories) {
            String currCatName = currCategory.getName().name();
            allCategoriesNames.add(currCatName);
        }
        return allCategoriesNames;
    }
}
