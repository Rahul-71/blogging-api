package com.blogapplication.blogappapis.services;

import java.util.List;

import com.blogapplication.blogappapis.payloads.PostDTO;
import com.blogapplication.blogappapis.payloads.PostResponse;

public interface PostService {
    // create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // update
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    // delete
    void deletePost(Integer postId);

    // get
    PostDTO getPost(Integer postId);

    // get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get all posts by user
    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get all posts by category
    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // search posts by keyword
    List<PostDTO> searchPosts(String keyword);
}
