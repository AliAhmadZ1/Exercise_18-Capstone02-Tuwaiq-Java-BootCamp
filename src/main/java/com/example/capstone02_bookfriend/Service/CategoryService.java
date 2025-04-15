package com.example.capstone02_bookfriend.Service;

import com.example.capstone02_bookfriend.ApiResponse.ApiException;
import com.example.capstone02_bookfriend.Model.Category;
import com.example.capstone02_bookfriend.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        Category oldCategory = categoryRepository.findCategoryByName(category.getName());

        if (oldCategory == null) {
            categoryRepository.save(category);
        }
        throw new ApiException("category name is already exist");
    }

    public void updateCategory(Integer id, Category category) {
        Category oldCategory = categoryRepository.findCategoryById(id);
        Category checkName = categoryRepository.findCategoryByName(category.getName());
        if (oldCategory == null || checkName != null)
            throw new ApiException("category not found or name already used");

        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findCategoryById(id);

        if (category == null)
            throw new ApiException("category not found");
        categoryRepository.delete(category);
    }

}
