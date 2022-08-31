package com.blogapplication.blogappapis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, message = "Username length must be greater than 3")
    private String name;

    @Email(message = "Please enter a valid emailId !!", regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}")
    private String emailId;

    @NotEmpty(message = "Password is mandatory !!")
    @Size(min = 3, max = 10, message = "Password length should be in range of 3 to 10.")
    private String password;

    @NotEmpty(message = "We are intrested to know something about you !!")
    private String about;

    private Set<CommentDTO> comments = new HashSet<>();

    private Set<RoleDTO> roles = new HashSet<>();
}
