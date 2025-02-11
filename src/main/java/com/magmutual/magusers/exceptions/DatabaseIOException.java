package com.magmutual.magusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseIOException extends RuntimeException {

    public DatabaseIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
