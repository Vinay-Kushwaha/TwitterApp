package com.twitterApplication.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, Object fieldValue) {
        super(String.format("%s not found with : '%s'", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
