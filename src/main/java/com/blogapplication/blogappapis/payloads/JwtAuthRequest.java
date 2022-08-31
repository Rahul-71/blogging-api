package com.blogapplication.blogappapis.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;

}
