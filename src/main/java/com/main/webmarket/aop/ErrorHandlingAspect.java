package com.main.webmarket.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
public class ErrorHandlingAspect {

    private record Message(String message, String description) {}

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(
            HttpClientErrorException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message("Error 400: Bad request", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<Object> handleNoResourceFoundException(
            NoHandlerFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Message("Error 404: Not Found", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> noSuchElementException(
            NoSuchElementException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Message("Error 404", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<Object> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new Message("Error 405: Method not supported", ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleAllExceptions(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Message("Error 500: Internal server error", ex.getMessage()));
    }
}
