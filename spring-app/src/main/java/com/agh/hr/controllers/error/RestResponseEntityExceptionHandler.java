package com.agh.hr.controllers.error;

import com.agh.hr.model.error.InvalidRequestException;
import com.agh.hr.model.error.NotAuthorizedException;
import lombok.extern.java.Log;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    private final Logger logger;

    public RestResponseEntityExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(value
            = {NotAuthorizedException.class})
    protected ResponseEntity<Void> handleNotAuthorized(NotAuthorizedException _ex) {
        logger.warn("Unauthorized request " + _ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(value
            = {InvalidRequestException.class})
    protected ResponseEntity<String> handleInvalidRequest(InvalidRequestException ex) {
        logger.warn("Invalid request " + ex.getMessage());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value
            = {Exception.class})
    protected ResponseEntity<String> generic(InvalidRequestException ex) {
        val buffer = new StringWriter();
        val pw = new PrintWriter(buffer);
        ex.printStackTrace(pw);

        logger.error(buffer.toString());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
