package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AreaNotFoundException extends RuntimeException{
    public AreaNotFoundException() {
        super();
    }

    public AreaNotFoundException(String message) {
        super(message);
    }

    public AreaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}