package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadUserRoleException extends RuntimeException{
    public BadUserRoleException() {
        super();
    }

    public BadUserRoleException(String message) {
        super(message);
    }

    public BadUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
