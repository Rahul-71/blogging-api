package com.blogapplication.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blogappapis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
