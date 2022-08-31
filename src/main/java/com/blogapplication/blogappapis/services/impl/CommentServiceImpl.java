package com.blogapplication.blogappapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.blogappapis.entities.Comment;
import com.blogapplication.blogappapis.entities.Post;
import com.blogapplication.blogappapis.entities.User;
import com.blogapplication.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapplication.blogappapis.payloads.CommentDTO;
import com.blogapplication.blogappapis.repositories.CommentRepo;
import com.blogapplication.blogappapis.repositories.PostRepo;
import com.blogapplication.blogappapis.repositories.UserRepo;
import com.blogapplication.blogappapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId: " + postId));

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId: " + userId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId: " + commentId));
        this.commentRepo.delete(comment);
    }

}
