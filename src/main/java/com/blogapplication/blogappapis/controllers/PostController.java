package com.blogapplication.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapplication.blogappapis.config.AppConstants;
import com.blogapplication.blogappapis.payloads.ApiResponse;
import com.blogapplication.blogappapis.payloads.PostDTO;
import com.blogapplication.blogappapis.payloads.PostResponse;
import com.blogapplication.blogappapis.services.FileService;
import com.blogapplication.blogappapis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String filePath;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // get all post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse posts = this.postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(posts);
    }

    // get all post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse posts = this.postService.getPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(posts);
    }

    // get post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        PostDTO posts = this.postService.getPost(postId);
        return ResponseEntity.ok(posts);
    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(allPost);
    }

    // delete
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully.", true));
    }

    // update
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId) {
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return ResponseEntity.ok(updatedPost);
    }

    // searching using keyword
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword) {
        List<PostDTO> searchResult = this.postService.searchPosts(keyword);
        return ResponseEntity.ok(searchResult);
    }

    // post image upload
    @PostMapping(value = "/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam(value = "image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDTO postdto = this.postService.getPost(postId);
        String uploadedImageName = this.fileService.uploadImage(filePath, image);
        postdto.setImageName(uploadedImageName);
        PostDTO updatedPost = this.postService.updatePost(postdto, postId);

        return ResponseEntity.ok(updatedPost);
    }

    // to serve image
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable(value = "imageName") String imageName, HttpServletResponse response)
            throws IOException {
        InputStream resourceInputStream = this.fileService.getResource(filePath, imageName);
        // response.setContentTupe is used to set the content type of our response which
        // we'll send with response
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // now since we have to serve file(in this case image). So, we'll stream down
        // our input to output stream
        StreamUtils.copy(resourceInputStream, response.getOutputStream());
    }

}
