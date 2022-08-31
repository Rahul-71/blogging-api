package com.blogapplication.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.blogappapis.entities.Category;
import com.blogapplication.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapplication.blogappapis.payloads.CategoryDTO;
import com.blogapplication.blogappapis.repositories.CategoryRepo;
import com.blogapplication.blogappapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId: " + categoryId));

        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryTitle());

        Category updatedCategory = this.categoryRepo.save(category);

        return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId: " + categoryId));
        this.categoryRepo.delete(category);

    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId: " + categoryId));

        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> allCategories = this.categoryRepo.findAll();
        List<CategoryDTO> categoriesDtos = allCategories.stream()
                .map(catg -> this.modelMapper.map(catg, CategoryDTO.class)).collect(Collectors.toList());
        return categoriesDtos;
    }

}
