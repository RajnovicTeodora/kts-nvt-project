package com.ftn.restaurant.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DishExistsException extends RuntimeException {

    public DishExistsException() {
        super();
    }

    public DishExistsException(String message) {
        super(message);
    }

    public DishExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
