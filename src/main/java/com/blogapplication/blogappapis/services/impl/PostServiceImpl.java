package com.blogapplication.blogappapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blogapplication.blogappapis.entities.Category;
import com.blogapplication.blogappapis.entities.Post;
import com.blogapplication.blogappapis.entities.User;
import com.blogapplication.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapplication.blogappapis.payloads.PostDTO;
import com.blogapplication.blogappapis.payloads.PostResponse;
import com.blogapplication.blogappapis.repositories.CategoryRepo;
import com.blogapplication.blogappapis.repositories.PostRepo;
import com.blogapplication.blogappapis.repositories.UserRepo;
import com.blogapplication.blogappapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRepo postRepo;
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private CategoryRepo categoryRepo;
        @Autowired
        private ModelMapper modelMapper;

        @Override
        public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
                Post post = this.modelMapper.map(postDTO, Post.class);
                post.setAddedDate(new Date());
                post.setImageName("default.png");

                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "userId: " + userId));
                Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                                () -> new ResourceNotFoundException("Category", "categoryId: " + categoryId));
                post.setUser(user);
                post.setCategory(category);

                Post createdPost = this.postRepo.save(post);
                return this.modelMapper.map(createdPost, PostDTO.class);
        }

        @Override
        public PostDTO updatePost(PostDTO postDTO, Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId: " + postId));
                post.setTitle(postDTO.getTitle());
                post.setContent(postDTO.getContent());
                post.setImageName(postDTO.getImageName());

                Post savedPost = this.postRepo.save(post);
                return this.modelMapper.map(savedPost, PostDTO.class);
        }

        @Override
        public void deletePost(Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId: " + postId));
                this.postRepo.delete(post);
        }

        @Override
        public PostDTO getPost(Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId: " + postId));
                return this.modelMapper.map(post, PostDTO.class);
        }

        @Override
        public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

                Direction sortDir = sortDirection.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
                PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sortBy));
                Page<Post> postPages = this.postRepo.findAll(pageRequest);
                List<Post> posts = postPages.getContent();

                List<PostDTO> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setPostContent(postDtos);
                postResponse.setPageNumber(postPages.getNumber());
                postResponse.setPageSize(postPages.getSize());
                postResponse.setTotalPages(postPages.getTotalPages());
                postResponse.setTotalElements(postPages.getTotalElements());
                postResponse.setLastPage(postPages.isLast());

                return postResponse;
        }

        @Override
        public List<PostDTO> searchPosts(String keyword) {
                List<Post> postsContainingKey = this.postRepo.findByTitleContaining(keyword);
                List<PostDTO> postDtos = postsContainingKey.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

                return postDtos;
        }

        @Override
        public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
                        String sortDirection) {
                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "userId: " + userId));

                Direction sortDir = sortDirection.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
                PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sortBy));

                Page<Post> postPages = this.postRepo.findByUser(user, pageRequest);
                List<Post> posts = postPages.getContent();
                List<PostDTO> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setPostContent(postDtos);
                postResponse.setPostContent(postDtos);
                postResponse.setPageNumber(postPages.getNumber());
                postResponse.setPageSize(postPages.getSize());
                postResponse.setTotalPages(postPages.getTotalPages());
                postResponse.setTotalElements(postPages.getTotalElements());
                postResponse.setLastPage(postPages.isLast());

                return postResponse;
        }

        @Override
        public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy,
                        String sortDirection) {

                Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                                () -> new ResourceNotFoundException("Category", "categoryId: " + categoryId));
                // List<Post> posts = this.postRepo.findByCategory(category);

                Direction sortDir = sortDirection.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
                PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir, sortBy));
                Page<Post> postPages = this.postRepo.findByCategory(category, pageRequest);
                List<Post> posts = postPages.getContent();
                List<PostDTO> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setPostContent(postDtos);
                postResponse.setPostContent(postDtos);
                postResponse.setPageNumber(postPages.getNumber());
                postResponse.setPageSize(postPages.getSize());
                postResponse.setTotalPages(postPages.getTotalPages());
                postResponse.setTotalElements(postPages.getTotalElements());
                postResponse.setLastPage(postPages.isLast());

                return postResponse;
        }

}
