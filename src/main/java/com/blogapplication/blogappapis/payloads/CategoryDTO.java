package com.blogapplication.blogappapis.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;

    @NotEmpty(message = "Please enter a title.")
    @Size(min = 4, message = "Title length should be more than 4 characters.")
    private String categoryTitle;
    
    @NotEmpty(message = "Please enter a description.")
    @Size(min = 10, message = "Title length should be more than 10 characters.")
    private String categoryDescription;

}
