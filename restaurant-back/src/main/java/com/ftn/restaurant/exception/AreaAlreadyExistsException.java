package com.ftn.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Area already exists")
public class AreaAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;
    public AreaAlreadyExistsException(String message) {
        super(message);
    }

}