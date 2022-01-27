package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantTableNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RestaurantTableNotFoundException() {
        super();
    }

    public RestaurantTableNotFoundException(String message) {
        super(message);
    }

    public RestaurantTableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
