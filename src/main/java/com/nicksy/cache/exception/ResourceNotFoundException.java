package com.nicksy.cache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // pass the incoming message to the parent RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
