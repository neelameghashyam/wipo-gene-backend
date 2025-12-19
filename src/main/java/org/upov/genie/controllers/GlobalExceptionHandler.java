package org.upov.genie.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.upov.genie.utils.ApiException;
import org.upov.genie.utils.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handle(ApiException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(ex.getStatus())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unexpected(Exception ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message("Internal server error")
                .status(500)
                .build();
        return ResponseEntity.status(500).body(err);
    }
}