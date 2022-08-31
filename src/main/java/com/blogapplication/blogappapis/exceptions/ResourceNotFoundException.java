package com.blogapplication.blogappapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    // String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldNameValue) {
        super(String.format("%s not found with %s", resourceName, fieldNameValue));
        this.resourceName = resourceName;
        this.fieldName = fieldNameValue;
        // this.fieldValue = fieldValue;
    }

}
