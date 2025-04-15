package com.example.capstone02_bookfriend.Controller;

import com.example.capstone02_bookfriend.ApiResponse.ApiResponse;
import com.example.capstone02_bookfriend.Model.Category;
import com.example.capstone02_bookfriend.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getAll() {
        if (categoryService.getAllCategories().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("there are no categories"));
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isAdded = categoryService.addCategory(category);
        if (isAdded)
            return ResponseEntity.status(200).body(new ApiResponse("new category is added"));
        return ResponseEntity.status(400).body(new ApiResponse("this category is already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id,@RequestBody@Valid Category category,Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("category is updated"));
        return ResponseEntity.status(400).body(new ApiResponse("not found or category is already exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("is deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

}
