package com.blogapplication.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blogappapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
