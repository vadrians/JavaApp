package com.bombk1n.materialpro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
