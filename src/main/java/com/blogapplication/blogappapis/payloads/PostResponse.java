package com.blogapplication.blogappapis.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> postContent;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;

}
