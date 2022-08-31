package com.blogapplication.blogappapis.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get JWT token from request
        String requestToken = request.getHeader("Authorization");
        // eg. of requestToken is "Bearer XXXX....."

        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired.");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token.");
            }
        } else {
            System.out.println("JWT Token does not starts with Bearer");
        }

        // 2. once we get the token then validate token

        // ie. token is associated with a user & rightnow securityContext is not holding
        // any authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // load user associated with token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // validate token using jwtTokenHelper class.
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {

                // set spring security (set authentication on spring security)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("invalid JWT token ");
            }
        } else {
            System.out.println("Username is NULL or securityContext is already set/not-null");
        }
        
// if till here every operation is done successfully, now filter this request further.

        filterChain.doFilter(request, response);

    }

}
