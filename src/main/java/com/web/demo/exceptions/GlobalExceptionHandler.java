package com.web.demo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<ProblemDetail> handleRejectedExecutionException(
            RejectedExecutionException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
        problem.setTitle("Execution Rejected");
        problem.setDetail("The server is overloaded. Please try again later.");
        problem.setType(URI.create("https://example.com/problems/execution-rejected"));
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", Instant.now());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(problem);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProblemDetailException(ResourceNotFoundException ex,HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(ex.getStatus());
        problem.setTitle("Execution Rejected");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(ex.getUrl()));
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", Instant.now());

        return ResponseEntity
                .status(ex.getStatus())
                .body(problem);
    }

    @ExceptionHandler(TimeoutException.class)
    public ProblemDetail handleTimeout(TimeoutException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problem.setTitle("Timeout");
        problem.setDetail(ex.getMessage());
        problem.setProperty("url", ex.getUrl());
        problem.setProperty("status", ex.getStatus());
        return problem;
    }

    @ExceptionHandler(RemoteServiceException.class)
    public ProblemDetail remoteServiceException(RemoteServiceException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problem.setTitle("Timeout");
        problem.setDetail(ex.getMessage());
        problem.setProperty("url", ex.getUrl());
        problem.setProperty("status", ex.getStatus());
        return problem;
    }
}

