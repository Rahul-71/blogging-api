package com.blogapplication.blogappapis.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapplication.blogappapis.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();

        // System.out.println("==============================");
        // ex.getBindingResult().getAllErrors().stream().forEach(e ->
        // System.out.println("==> "+e));
        // System.out.println("==============================");

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            String fieldName = ((FieldError) error).getField();
            response.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
}
