package com.magmutual.magusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseFormatException extends RuntimeException {
    public DatabaseFormatException(String message, Throwable cause) {
    }
}
