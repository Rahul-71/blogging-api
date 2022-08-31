package com.blogapplication.blogappapis.services;

import java.util.List;

import com.blogapplication.blogappapis.payloads.CategoryDTO;

public interface CategoryService {
    // create
    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    // update
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    // delete
    public void deleteCategory(Integer categoryId);

    // get
    public CategoryDTO getCategory(Integer categoryId);

    // getAll
    public List<CategoryDTO> getCategories();
}
