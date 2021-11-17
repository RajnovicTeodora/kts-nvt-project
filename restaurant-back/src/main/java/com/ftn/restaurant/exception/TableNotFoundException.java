package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TableNotFoundException extends RuntimeException {

    public TableNotFoundException() {
        super();
    }

    public TableNotFoundException(String message) {
        super(message);
    }

    public TableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}