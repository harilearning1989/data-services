package com.web.demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class TimeoutException extends RuntimeException {
    @Getter
    private final String url;
    @Getter
    private final HttpStatus status;
    private final String message;

    public TimeoutException(String url, HttpStatus status, String message) {
        super(message);
        this.url = url;
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

