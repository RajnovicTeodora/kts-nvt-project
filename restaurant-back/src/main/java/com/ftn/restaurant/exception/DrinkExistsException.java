package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DrinkExistsException extends RuntimeException {

    public DrinkExistsException() {
        super();
    }

    public DrinkExistsException(String message) {
        super(message);
    }

    public DrinkExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
