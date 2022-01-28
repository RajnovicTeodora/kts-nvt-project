package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OrderAlreadyPaidException extends RuntimeException{
    public OrderAlreadyPaidException() {
        super();
    }

    public OrderAlreadyPaidException(String message) {
        super(message);
    }

    public OrderAlreadyPaidException(String message, Throwable cause) {
        super(message, cause);
    }
}
