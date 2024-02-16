package com.pnvtm.hfs.cr.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

@ControllerAdvice
public class AppExceptionHandler
        extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class,
            NumberFormatException.class})
    protected ResponseEntity<Object> handleIllegalArgument(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Incorrect incoming arguments";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Not Found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
