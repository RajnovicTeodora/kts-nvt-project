package com.ftn.restaurant.exception;

public class AreaAlreadyExistsException extends RuntimeException{
    public AreaAlreadyExistsException() {
        super();
    }

    public AreaAlreadyExistsException(String message) {
        super(message);
    }

    public AreaAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}