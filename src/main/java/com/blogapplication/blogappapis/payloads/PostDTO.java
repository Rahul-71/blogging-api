package com.blogapplication.blogappapis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Integer postId;
    @NotEmpty(message = "Please enter a title.")
    @Size(min = 10, max = 100, message = "Enter title within 10-100 characters.")
    private String title;
    @NotEmpty(message = "Please write some desired content.")
    @Size(min = 10, max = 10000, message = "Content should be within 50 to 10,000 characters.")
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;
    private Set<CommentDTO> comments = new HashSet<>();
}
