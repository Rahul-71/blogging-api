package com.blogapplication.blogappapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.blogappapis.payloads.ApiResponse;
import com.blogapplication.blogappapis.payloads.UserDTO;
import com.blogapplication.blogappapis.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // POST - means create new user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // PUT - means update a user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
            @PathVariable("userId") Integer uId) {
        UserDTO updatedUser = this.userService.updateUser(userDTO, uId);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE - means to delete a user
    // this operation can only be done by ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    // GET - means fetching user
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
        UserDTO user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

}
