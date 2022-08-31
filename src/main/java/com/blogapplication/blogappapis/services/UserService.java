package com.blogapplication.blogappapis.services;

import java.util.List;

import com.blogapplication.blogappapis.payloads.UserDTO;

public interface UserService {

    // registering new user
    UserDTO registerNewUser(UserDTO user);
    
    // creating a new user
    UserDTO createUser(UserDTO user);
    
    // updating data of existing user
    UserDTO updateUser(UserDTO user, Integer UserId);
    
    // fetching details of user by userId
    UserDTO getUserById(Integer UserId);
    
    // fetching details of all user
    List<UserDTO> getAllUsers();
    
    // deleting existing user
    void deleteUser(Integer userId);
}
