package com.blogapplication.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapplication.blogappapis.config.AppConstants;
import com.blogapplication.blogappapis.entities.Role;
import com.blogapplication.blogappapis.entities.User;
import com.blogapplication.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapplication.blogappapis.payloads.UserDTO;
import com.blogapplication.blogappapis.repositories.RoleRepo;
import com.blogapplication.blogappapis.repositories.UserRepo;
import com.blogapplication.blogappapis.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = dtoToEntity(userDto);
        User savedUser = this.userRepo.save(user); // this method needs entity not dto object
        return entityToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id: " + userId));
        user.setAbout(userDto.getAbout());
        user.setEmailId(userDto.getEmailId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        User savedUser = this.userRepo.save(user);
        UserDTO updatedUser = this.entityToDto(savedUser);

        return updatedUser;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id: " + userId));
        return this.entityToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userLists = this.userRepo.findAll();
        List<UserDTO> userDtos = userLists.stream().map(user -> this.entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id: " + userId));
        this.userRepo.delete(user);
    }

    private User dtoToEntity(UserDTO userDto) {
        // User user = new User();
        // user.setAbout(userDto.getAbout());
        // user.setEmailId(userDto.getEmailId());
        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setPassword(userDto.getPassword());

        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDTO entityToDto(User user) {
        // UserDTO userDto = new UserDTO();
        // userDto.setAbout(user.getAbout());
        // userDto.setEmailId(user.getEmailId());
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setPassword(user.getPassword());

        UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    @Override
    public UserDTO registerNewUser(UserDTO userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // retrieving & setting the password with encoded one.
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // if any user is registered, bydefualt it will be assigned a NORMAL user.
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        this.userRepo.save(user);

        return this.modelMapper.map(user, UserDTO.class);
    }

}
