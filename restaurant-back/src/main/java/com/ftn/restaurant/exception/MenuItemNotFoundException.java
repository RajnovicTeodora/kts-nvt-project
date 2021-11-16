package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException() {
        super();
    }

    public MenuItemNotFoundException(String message) {
        super(message);
    }

    public MenuItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}