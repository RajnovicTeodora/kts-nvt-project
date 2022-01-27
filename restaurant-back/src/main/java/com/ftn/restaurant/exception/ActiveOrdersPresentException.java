package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ActiveOrdersPresentException extends RuntimeException {

    public ActiveOrdersPresentException() {
        super();
    }

    public ActiveOrdersPresentException(String message) {
        super(message);
    }

    public ActiveOrdersPresentException(String message, Throwable cause) {
        super(message, cause);
    }
}
