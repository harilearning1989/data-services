package com.web.demo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RemoteServiceException extends RuntimeException {

    private final String url;
    private final HttpStatusCode status;
    private final String errorMessage;

    public RemoteServiceException(String url, HttpStatusCode status, String errorMessage) {
        super(String.format("Error while calling %s: [%d %s] - %s",
                url, status.value(), status.value(), errorMessage));
        this.url = url;
        this.status = status;
        this.errorMessage = errorMessage;
    }

}
