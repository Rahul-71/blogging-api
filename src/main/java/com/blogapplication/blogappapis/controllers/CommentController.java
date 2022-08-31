package com.blogapplication.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.blogappapis.payloads.ApiResponse;
import com.blogapplication.blogappapis.payloads.CommentDTO;
import com.blogapplication.blogappapis.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDto,
     @PathVariable Integer postId,
     @RequestParam(value = "userId", required = true) Integer userId
     ) {

        CommentDTO createdComment = this.commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);

    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        ApiResponse response = new ApiResponse("Comment deleted successfully!", true);
        return ResponseEntity.ok(response);
    }

}
