package com.bsa.bsagiphy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Object> handleApplicationBusinessLogicException(InvalidArgumentException ex) {
        return ResponseEntity
                .unprocessableEntity()
                .body(
                        Map.of(
                                "error", ex.getMessage() == null ? "Unprocessable request parameters" : ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(UnavailableResourceException.class)
    public ResponseEntity<Object> handleUnavailableResourceException(UnavailableResourceException e) {
        return ResponseEntity
                .badRequest().build();
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
