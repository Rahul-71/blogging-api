package com.blogapplication.blogappapis.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blogappapis.entities.Category;
import com.blogapplication.blogappapis.entities.Post;
import com.blogapplication.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    // List<Post> findByUser(User user);
    // List<Post> findByCategory(Category category);
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    List<Post> findByTitleContaining(String keyword);
}
