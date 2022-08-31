package com.blogapplication.blogappapis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

    // token validity in millisec
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret = "jwtTokenKey";

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    // retrieves Claims from a jwt token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retriving all information from token we need secret-key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

    // check if token has expired.
    public Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    // generate token from user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return this.doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * while creating the token - (1.) Define claims of the token, like
     * Issuer,Expiration, Subject, and the ID (2.) Sign the JWT using the HS512
     * algorithm and secret key. (3.) According to JWS Compact
     * Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * compaction of the JWT to a URL-safe string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }

}
