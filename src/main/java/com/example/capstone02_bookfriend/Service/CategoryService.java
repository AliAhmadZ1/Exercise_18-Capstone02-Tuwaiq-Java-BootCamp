package com.example.capstone02_bookfriend.Service;

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

    public Boolean addCategory(Category category) {
        Category oldCategory = categoryRepository.findCategoryByName(category.getName());

        if (oldCategory == null) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public Boolean updateCategory(Integer id, Category category) {
        Category oldCategory = categoryRepository.findCategoryById(id);
        Category checkName = categoryRepository.findCategoryByName(category.getName());
        if (oldCategory == null || checkName != null)
            return false;

        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;
    }

    public Boolean deleteCategory(Integer id) {
        Category category = categoryRepository.findCategoryById(id);

        if (category == null)
            return false;
        categoryRepository.delete(category);
        return true;
    }

}
