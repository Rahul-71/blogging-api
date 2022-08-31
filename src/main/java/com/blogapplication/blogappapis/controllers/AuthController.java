package com.blogapplication.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.blogappapis.exceptions.ApiException;
import com.blogapplication.blogappapis.payloads.JwtAuthRequest;
import com.blogapplication.blogappapis.payloads.JwtAuthResponse;
import com.blogapplication.blogappapis.payloads.UserDTO;
import com.blogapplication.blogappapis.security.JwtTokenHelper;
import com.blogapplication.blogappapis.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest) throws Exception {
        this.authenticate(authRequest.getUsername(), authRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());

        String generatedToken = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(generatedToken);

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = this.userService.registerNewUser(userDTO);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authentication;
        authentication = new UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid Username or Password !!");
            // throw new Exception(e.getMessage());
        }
    }
}
