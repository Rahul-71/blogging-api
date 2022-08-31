package com.blogapplication.blogappapis.services;

import com.blogapplication.blogappapis.payloads.CommentDTO;

public interface CommentService {

    // create comment
    CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId);

    // delete comment
    void deleteComment(Integer commentId);

    // get comment
    // CommentDTO getComment(Integer commentId); 

}
