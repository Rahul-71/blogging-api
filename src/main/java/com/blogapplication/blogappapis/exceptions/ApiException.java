package com.blogapplication.blogappapis.exceptions;

public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
