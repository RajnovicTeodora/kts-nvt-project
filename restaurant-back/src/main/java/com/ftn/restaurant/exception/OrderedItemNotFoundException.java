package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderedItemNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public OrderedItemNotFoundException() {
        super();
    }

    public OrderedItemNotFoundException(String message) {
        super(message);
    }

    public OrderedItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
