package com.blogapplication.blogappapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.blogappapis.payloads.ApiResponse;
import com.blogapplication.blogappapis.payloads.CategoryDTO;
import com.blogapplication.blogappapis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Integer categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, categoryId);

        return ResponseEntity.ok(updatedCategory);
    }

    // delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(new ApiResponse("Category deleted successfully!!", true));
    }

    // get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId) {
        CategoryDTO category = this.categoryService.getCategory(categoryId);

        return ResponseEntity.ok(category);
    }

    // getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
