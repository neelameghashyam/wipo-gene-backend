package org.upov.genie.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

public class GenieApiException extends RuntimeException {
    public GenieApiException(String message) {
        super(message);
    }
    
    public GenieApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

@Data
@AllArgsConstructor
class GenieErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

@RestControllerAdvice
class GlobalGenieExceptionHandler {
    
    @ExceptionHandler(GenieApiException.class)
    public ResponseEntity<GenieErrorResponse> handleGenieApiException(
        GenieApiException ex,
        jakarta.servlet.http.HttpServletRequest request
    ) {
        GenieErrorResponse error = new GenieErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenieErrorResponse> handleGenericException(
        Exception ex,
        jakarta.servlet.http.HttpServletRequest request
    ) {
        GenieErrorResponse error = new GenieErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred: " + ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

