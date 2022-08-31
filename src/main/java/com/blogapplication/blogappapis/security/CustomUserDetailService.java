package com.blogapplication.blogappapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapplication.blogappapis.entities.User;
import com.blogapplication.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapplication.blogappapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = this.userRepo.findByEmailId(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email: " + username));

        // its return type is UserDetails but we are returning User type
        // because User class implements UserDetails
        return user;
    }

}
